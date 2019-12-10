pragma solidity 0.5.6;

import "./Utils.sol";

import "./AssetNetwork.sol";

/// @title AssetNetworkRegistry
/// @notice The AssetNetwork Registry deploys new AssetNetwork contracts for the
/// Raiden Network protocol.
contract AssetNetworkRegistry is Utils {
    address public secret_registry_address;
    uint256 public chain_id;
    uint256 public settlement_timeout_min;
    uint256 public settlement_timeout_max;
    uint256 public max_asset_networks;

    // Only for the limited Red Eyes release
    address public deprecation_executor;
    uint256 public asset_network_created = 0;

    // Asset address => AssetNetwork address
    mapping(address => address) public asset_to_asset_networks;

    event AssetNetworkCreated(address indexed asset_address, address indexed asset_network_address);

    modifier canCreateAssetNetwork() {
        require(asset_network_created < max_asset_networks, "registry full");
        _;
    }

    /// @param _secret_registry_address The address of SecretRegistry that's used by all
    /// AssetNetworks created by this contract.
    /// @param _chain_id EIP-155 Chain-ID of the chain where this contract is deployed.
    /// @param _settlement_timeout_min The shortest settlement period (in number of blocks)
    /// that can be chosen at the channel opening.
    /// @param _settlement_timeout_max The longest settlement period (in number of blocks)
    /// that can be chosen at the channel opening.
    /// @param _max_asset_networks the number of assets that can be registered.
    /// MAX_UINT256 means no limits.
    constructor(
        address _secret_registry_address,
        uint256 _chain_id,
        uint256 _settlement_timeout_min,
        uint256 _settlement_timeout_max,
        uint256 _max_asset_networks
    )
        public
    {
        require(_chain_id > 0);
        require(_settlement_timeout_min > 0);
        require(_settlement_timeout_max > 0);
        require(_settlement_timeout_max > _settlement_timeout_min);
        require(_secret_registry_address != address(0x0));
        require(contractExists(_secret_registry_address));
        require(_max_asset_networks > 0);
        secret_registry_address = _secret_registry_address;
        chain_id = _chain_id;
        settlement_timeout_min = _settlement_timeout_min;
        settlement_timeout_max = _settlement_timeout_max;
        max_asset_networks = _max_asset_networks;

        deprecation_executor = msg.sender;
    }

    /// @notice Deploy a new AssetNetwork contract for the Asset deployed at
    /// `_asset_address`.
    /// @param _asset_address Ethereum address of an already deployed asset, to
    /// be used in the new AssetNetwork contract.
    function createBAC001AssetNetwork(
        address _asset_address,
        uint256 _channel_participant_deposit_limit,
        uint256 _asset_network_deposit_limit
    )
        external
        canCreateAssetNetwork
        returns (address asset_network_address)
    {
        require(asset_to_asset_networks[_asset_address] == address(0x0));

        // We limit the number of asset networks to 1 for the Bug Bounty release
        asset_network_created = asset_network_created + 1;

        AssetNetwork asset_network;

        // Asset contract checks are in the corresponding AssetNetwork contract
        asset_network = new AssetNetwork(
            _asset_address,
            secret_registry_address,
            chain_id,
            settlement_timeout_min,
            settlement_timeout_max,
            deprecation_executor,
            _channel_participant_deposit_limit,
            _asset_network_deposit_limit
        );

        asset_network_address = address(asset_network);

        asset_to_asset_networks[_asset_address] = asset_network_address;
        emit AssetNetworkCreated(_asset_address, asset_network_address);

        return asset_network_address;
    }
}

