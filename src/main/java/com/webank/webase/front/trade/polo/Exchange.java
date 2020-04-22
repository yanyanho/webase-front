package com.webank.webase.front.trade.polo;

import io.reactivex.Flowable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint8;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Exchange extends Contract {
    public static final String BINARY = "608060405260006003556000600455600060055534801561001f57600080fd5b5060405160208061305e83398101806040528101908080519060200190929190505050806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050612f4a806101146000396000f300608060405260043610610133576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630a19b14a146101385780630b9276661461021657806319774d43146102bd5780632108234d14610322578063278b8c0e1461039957806338e4f0641461044d57806346a5d0431461049a57806346be96c3146104e757806354d03b5c146105cf57806357786394146105fc5780635e1d7ae41461062757806365e17c9d1461065457806371ffcb16146106ab578063731c2f81146106ee5780638823a9c0146107195780638f28397014610746578063bb5f462914610789578063c281309e146107f2578063e8f6bc2e1461081d578063f341294214610860578063f7888aec146108b7578063f851a4401461092e578063fb6e155f14610985575b600080fd5b34801561014457600080fd5b50610214600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291908035906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803560ff1690602001909291908035600019169060200190929190803560001916906020019092919080359060200190929190505050610a6d565b005b34801561022257600080fd5b5061029f600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919080359060200190929190803590602001909291905050506110c1565b60405180826000191660001916815260200191505060405180910390f35b3480156102c957600080fd5b5061030c600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035600019169060200190929190505050611361565b6040518082815260200191505060405180910390f35b34801561032e57600080fd5b50610383600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611386565b6040518082815260200191505060405180910390f35b3480156103a557600080fd5b5061044b600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291908035906020019092919080359060200190929190803560ff169060200190929190803560001916906020019092919080356000191690602001909291905050506113ab565b005b34801561045957600080fd5b50610498600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050611881565b005b3480156104a657600080fd5b506104e5600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050611c2a565b005b3480156104f357600080fd5b506105b9600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291908035906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803560ff16906020019092919080356000191690602001909291908035600019169060200190929190505050611f7e565b6040518082815260200191505060405180910390f35b3480156105db57600080fd5b506105fa60048036038101908080359060200190929190505050612123565b005b34801561060857600080fd5b50610611612197565b6040518082815260200191505060405180910390f35b34801561063357600080fd5b506106526004803603810190808035906020019092919050505061219d565b005b34801561066057600080fd5b5061066961221d565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156106b757600080fd5b506106ec600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050612243565b005b3480156106fa57600080fd5b506107036122e2565b6040518082815260200191505060405180910390f35b34801561072557600080fd5b50610744600480360381019080803590602001909291905050506122e8565b005b34801561075257600080fd5b50610787600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050612368565b005b34801561079557600080fd5b506107d8600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035600019169060200190929190505050612406565b604051808215151515815260200191505060405180910390f35b3480156107fe57600080fd5b50610807612435565b6040518082815260200191505060405180910390f35b34801561082957600080fd5b5061085e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061243b565b005b34801561086c57600080fd5b506108756124da565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156108c357600080fd5b50610918600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050612500565b6040518082815260200191505060405180910390f35b34801561093a57600080fd5b50610943612587565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561099157600080fd5b50610a57600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291908035906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803560ff169060200190929190803560001916906020019092919080356000191690602001909291905050506125ac565b6040518082815260200191505060405180910390f35b60006002308d8d8d8d8d8d604051808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018481526020018381526020018281526020019750505050505050506020604051808303816000865af1158015610b7f573d6000803e3d6000fd5b5050506040513d6020811015610b9457600080fd5b81019080805190602001909291905050509050600760008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000826000191660001916815260200190815260200160002060009054906101000a900460ff1680610ce057508573ffffffffffffffffffffffffffffffffffffffff166001826040518082600019166000191681526020019150506040518091039020878787604051600081526020016040526040518085600019166000191681526020018460ff1660ff1681526020018360001916600019168152602001826000191660001916815260200194505050505060206040516020810390808403906000865af1158015610cbe573d6000803e3d6000fd5b5050506020604051035173ffffffffffffffffffffffffffffffffffffffff16145b1515610d7a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001807f6f72646572206e6f74206578697374206f72207369676e6174757265206e6f7481526020017f207472756500000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b874211151515610df2576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f6f726465722074696d652065787069726573000000000000000000000000000081525060200191505060405180910390fd5b8a610e55600860008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600084600019166000191681526020019081526020016000205484612a51565b11151515610ecb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260108152602001807f616d6f756e74206578636573736564210000000000000000000000000000000081525060200191505060405180910390fd5b610ed98c8c8c8c8a87612a7b565b610f3b600860008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600083600019166000191681526020019081526020016000205483612a51565b600860008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008360001916600019168152602001908152602001600020819055507f6effdda786735d5033bfad5f53e5131abcced9e52be6c507b62d639685fbed6d8c838c8e868e02811515610fc857fe5b048a33604051808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001965050505050505060405180910390a1505050505050505050505050565b6000600230888888888888604051808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018481526020018381526020018281526020019750505050505050506020604051808303816000865af11580156111d3573d6000803e3d6000fd5b5050506040513d60208110156111e857600080fd5b810190808051906020019092919050505090506001600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000836000191660001916815260200190815260200160002060006101000a81548160ff0219169083151502179055507f3f7f2eda73683c21a15f9435af1028c93185b5f1fa38270762dc32be606b3e8587878787878733604051808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018781526020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018581526020018481526020018381526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200197505050505050505060405180910390a19695505050505050565b6008602052816000526040600020602052806000526040600020600091509150505481565b6006602052816000526040600020602052806000526040600020600091509150505481565b60006002308b8b8b8b8b8b604051808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018481526020018381526020018281526020019750505050505050506020604051808303816000865af11580156114bd573d6000803e3d6000fd5b5050506040513d60208110156114d257600080fd5b81019080805190602001909291905050509050600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000826000191660001916815260200190815260200160002060009054906101000a900460ff1615156115bf576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600f8152602001807f6f72646572206e6f74206578697374000000000000000000000000000000000081525060200191505060405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff166001826040518082600019166000191681526020019150506040518091039020868686604051600081526020016040526040518085600019166000191681526020018460ff1660ff1681526020018360001916600019168152602001826000191660001916815260200194505050505060206040516020810390808403906000865af115801561166a573d6000803e3d6000fd5b5050506020604051035173ffffffffffffffffffffffffffffffffffffffff161415156116ff576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f7369676e206e6f74206d6174636800000000000000000000000000000000000081525060200191505060405180910390fd5b88600860003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008360001916600019168152602001908152602001600020819055507f1e0b760c386003e9cb9bcf4fcf3997886042859d9b6ed6320e804597fcdb28b08a8a8a8a8a8a338b8b8b604051808b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018a81526020018973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018881526020018781526020018681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018460ff1660ff168152602001836000191660001916815260200182600019166000191681526020019a505050505050505050505060405180910390a150505050505050505050565b60008273ffffffffffffffffffffffffffffffffffffffff1614156118a557600080fd5b80600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101561192e57600080fd5b6119b4600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482612ec3565b600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508173ffffffffffffffffffffffffffffffffffffffff1663f49bb76b33836040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828152602001806020018281038252600081526020016020019350505050600060405180830381600087803b158015611aeb57600080fd5b505af1158015611aff573d6000803e3d6000fd5b505050507ff341246adaac6f497bc2a656f546ab9e182111d630394f0c57c710a59a2cb567823383600660008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054604051808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183815260200182815260200194505050505060405180910390a15050565b60008273ffffffffffffffffffffffffffffffffffffffff161415611c4e57600080fd5b8173ffffffffffffffffffffffffffffffffffffffff1663cd8e473b3330846040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200180602001828103825260008152602001602001945050505050600060405180830381600087803b158015611d3957600080fd5b505af1158015611d4d573d6000803e3d6000fd5b50505050611dd7600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482612a51565b600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055507fdcbc1c05240f31ff3ad067ef1ee35ce4997762752e3a095284754544f4c709d7823383600660008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054604051808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183815260200182815260200194505050505060405180910390a15050565b6000806002308d8d8d8d8d8d604051808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018481526020018381526020018281526020019750505050505050506020604051808303816000865af1158015612091573d6000803e3d6000fd5b5050506040513d60208110156120a657600080fd5b81019080805190602001909291905050509050600860008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008260001916600019168152602001908152602001600020549150509a9950505050505050505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561217e57600080fd5b60035481111561218d57600080fd5b8060038190555050565b60035481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156121f857600080fd5b600554811080612209575060045481115b1561221357600080fd5b8060058190555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561229e57600080fd5b80600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60055481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561234357600080fd5b600454811180612354575060055481105b1561235e57600080fd5b8060048190555050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156123c357600080fd5b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60076020528160005260406000206020528060005260406000206000915091509054906101000a900460ff1681565b60045481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561249657600080fd5b80600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905092915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000806000806002308f8f8f8f8f8f604051808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018481526020018381526020018281526020019750505050505050506020604051808303816000865af11580156126c2573d6000803e3d6000fd5b5050506040513d60208110156126d757600080fd5b81019080805190602001909291905050509250894211151515612762576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600c8152602001807f74696d652065787069726573000000000000000000000000000000000000000081525060200191505060405180910390fd5b600760008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000846000191660001916815260200190815260200160002060009054906101000a900460ff168061289b57508773ffffffffffffffffffffffffffffffffffffffff166001846040518082600019166000191681526020019150506040518091039020898989604051600081526020016040526040518085600019166000191681526020018460ff1660ff1681526020018360001916600019168152602001826000191660001916815260200194505050505060206040516020810390808403906000865af1158015612879573d6000803e3d6000fd5b5050506020604051035173ffffffffffffffffffffffffffffffffffffffff16145b1515612935576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260298152602001807f6f72646572206e6f74206578697374206f72207369676e61747572652063686581526020017f636b206661696c6564000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b6129978d600860008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000866000191660001916815260200190815260200160002054612ec3565b91508a612a20600660008f73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548f612edc565b811515612a2957fe5b04905080821015612a3c57819350612a40565b8093505b5050509a9950505050505050505050565b6000808284019050612a71848210158015612a6c5750838210155b612f0f565b8091505092915050565b612b01600660008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482612ec3565b600660008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550612c07600660008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482612a51565b600660008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550612d21600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205486612d128685612edc565b811515612d1b57fe5b04612ec3565b600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550612e3b600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205486612e2c8685612edc565b811515612e3557fe5b04612a51565b600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550505050505050565b6000612ed183831115612f0f565b818303905092915050565b6000808284029050612f056000851480612f005750838583811515612efd57fe5b04145b612f0f565b8091505092915050565b801515612f1b57600080fd5b505600a165627a7a72305820a4a7879abbc6cce7595d31949515a65e0a0d1864b1103ee91a064ddfd04576f60029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"assetGet\",\"type\":\"address\"},{\"name\":\"amountGet\",\"type\":\"uint256\"},{\"name\":\"assetGive\",\"type\":\"address\"},{\"name\":\"amountGive\",\"type\":\"uint256\"},{\"name\":\"expires\",\"type\":\"uint256\"},{\"name\":\"nonce\",\"type\":\"uint256\"},{\"name\":\"user\",\"type\":\"address\"},{\"name\":\"v\",\"type\":\"uint8\"},{\"name\":\"r\",\"type\":\"bytes32\"},{\"name\":\"s\",\"type\":\"bytes32\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"trade\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"assetGet\",\"type\":\"address\"},{\"name\":\"amountGet\",\"type\":\"uint256\"},{\"name\":\"assetGive\",\"type\":\"address\"},{\"name\":\"amountGive\",\"type\":\"uint256\"},{\"name\":\"expires\",\"type\":\"uint256\"},{\"name\":\"nonce\",\"type\":\"uint256\"}],\"name\":\"order\",\"outputs\":[{\"name\":\"hash\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"orderFills\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"address\"}],\"name\":\"assets\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"assetGet\",\"type\":\"address\"},{\"name\":\"amountGet\",\"type\":\"uint256\"},{\"name\":\"assetGive\",\"type\":\"address\"},{\"name\":\"amountGive\",\"type\":\"uint256\"},{\"name\":\"expires\",\"type\":\"uint256\"},{\"name\":\"nonce\",\"type\":\"uint256\"},{\"name\":\"v\",\"type\":\"uint8\"},{\"name\":\"r\",\"type\":\"bytes32\"},{\"name\":\"s\",\"type\":\"bytes32\"}],\"name\":\"cancelOrder\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"asset\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"withdrawAsset\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"asset\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"depositAsset\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"assetGet\",\"type\":\"address\"},{\"name\":\"amountGet\",\"type\":\"uint256\"},{\"name\":\"assetGive\",\"type\":\"address\"},{\"name\":\"amountGive\",\"type\":\"uint256\"},{\"name\":\"expires\",\"type\":\"uint256\"},{\"name\":\"nonce\",\"type\":\"uint256\"},{\"name\":\"user\",\"type\":\"address\"},{\"name\":\"v\",\"type\":\"uint8\"},{\"name\":\"r\",\"type\":\"bytes32\"},{\"name\":\"s\",\"type\":\"bytes32\"}],\"name\":\"amountFilled\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"feeMake_\",\"type\":\"uint256\"}],\"name\":\"changeFeeMake\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"feeMake\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"feeRebate_\",\"type\":\"uint256\"}],\"name\":\"changeFeeRebate\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"feeAccount\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"feeAccount_\",\"type\":\"address\"}],\"name\":\"changeFeeAccount\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"feeRebate\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"feeTake_\",\"type\":\"uint256\"}],\"name\":\"changeFeeTake\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"admin_\",\"type\":\"address\"}],\"name\":\"changeAdmin\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"orders\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"feeTake\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"accountLevelsAddr_\",\"type\":\"address\"}],\"name\":\"changeAccountLevelsAddr\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"accountLevelsAddr\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"asset\",\"type\":\"address\"},{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"admin\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"assetGet\",\"type\":\"address\"},{\"name\":\"amountGet\",\"type\":\"uint256\"},{\"name\":\"assetGive\",\"type\":\"address\"},{\"name\":\"amountGive\",\"type\":\"uint256\"},{\"name\":\"expires\",\"type\":\"uint256\"},{\"name\":\"nonce\",\"type\":\"uint256\"},{\"name\":\"user\",\"type\":\"address\"},{\"name\":\"v\",\"type\":\"uint8\"},{\"name\":\"r\",\"type\":\"bytes32\"},{\"name\":\"s\",\"type\":\"bytes32\"}],\"name\":\"availableVolume\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"name\":\"admin_\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"assetGet\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amountGet\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"assetGive\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amountGive\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"expires\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"nonce\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"user\",\"type\":\"address\"}],\"name\":\"Order\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"assetGet\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amountGet\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"assetGive\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amountGive\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"expires\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"nonce\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"user\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"v\",\"type\":\"uint8\"},{\"indexed\":false,\"name\":\"r\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"s\",\"type\":\"bytes32\"}],\"name\":\"Cancel\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"assetGet\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amountGet\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"assetGive\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amountGive\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"get\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"give\",\"type\":\"address\"}],\"name\":\"Trade\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"asset\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"user\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"balance\",\"type\":\"uint256\"}],\"name\":\"Deposit\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"asset\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"user\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"balance\",\"type\":\"uint256\"}],\"name\":\"Withdraw\",\"type\":\"event\"}]";

    public static final String FUNC_TRADE = "trade";

    public static final String FUNC_ORDER = "order";

    public static final String FUNC_ORDERFILLS = "orderFills";

    public static final String FUNC_ASSETS = "assets";

    public static final String FUNC_CANCELORDER = "cancelOrder";

    public static final String FUNC_WITHDRAWASSET = "withdrawAsset";

    public static final String FUNC_DEPOSITASSET = "depositAsset";

    public static final String FUNC_AMOUNTFILLED = "amountFilled";

    public static final String FUNC_CHANGEFEEMAKE = "changeFeeMake";

    public static final String FUNC_FEEMAKE = "feeMake";

    public static final String FUNC_CHANGEFEEREBATE = "changeFeeRebate";

    public static final String FUNC_FEEACCOUNT = "feeAccount";

    public static final String FUNC_CHANGEFEEACCOUNT = "changeFeeAccount";

    public static final String FUNC_FEEREBATE = "feeRebate";

    public static final String FUNC_CHANGEFEETAKE = "changeFeeTake";

    public static final String FUNC_CHANGEADMIN = "changeAdmin";

    public static final String FUNC_ORDERS = "orders";

    public static final String FUNC_FEETAKE = "feeTake";

    public static final String FUNC_CHANGEACCOUNTLEVELSADDR = "changeAccountLevelsAddr";

    public static final String FUNC_ACCOUNTLEVELSADDR = "accountLevelsAddr";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_AVAILABLEVOLUME = "availableVolume";

    public static final Event ORDER_EVENT = new Event("Order",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event CANCEL_EVENT = new Event("Cancel",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TRADE_EVENT = new Event("Trade",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event DEPOSIT_EVENT = new Event("Deposit",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAW_EVENT = new Event("Withdraw",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Exchange(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Exchange(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Exchange(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Exchange(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> trade(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce, String user, BigInteger v, byte[] r, byte[] s, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRADE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce),
                new org.fisco.bcos.web3j.abi.datatypes.Address(user),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void trade(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce, String user, BigInteger v, byte[] r, byte[] s, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRADE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce),
                new org.fisco.bcos.web3j.abi.datatypes.Address(user),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String tradeSeq(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce, String user, BigInteger v, byte[] r, byte[] s, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRADE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce),
                new org.fisco.bcos.web3j.abi.datatypes.Address(user),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> order(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce) {
        final Function function = new Function(
                FUNC_ORDER,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void order(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ORDER,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String orderSeq(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce) {
        final Function function = new Function(
                FUNC_ORDER,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<BigInteger> orderFills(String param0, byte[] param1) {
        final Function function = new Function(FUNC_ORDERFILLS,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(param0),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> assets(String param0, String param1) {
        final Function function = new Function(FUNC_ASSETS,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(param0),
                new org.fisco.bcos.web3j.abi.datatypes.Address(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> cancelOrder(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_CANCELORDER,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void cancelOrder(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce, BigInteger v, byte[] r, byte[] s, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CANCELORDER,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String cancelOrderSeq(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_CANCELORDER,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> withdrawAsset(String asset, BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAWASSET,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(asset),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void withdrawAsset(String asset, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_WITHDRAWASSET,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(asset),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String withdrawAssetSeq(String asset, BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAWASSET,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(asset),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> depositAsset(String asset, BigInteger amount) {
        final Function function = new Function(
                FUNC_DEPOSITASSET,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(asset),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void depositAsset(String asset, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_DEPOSITASSET,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(asset),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String depositAssetSeq(String asset, BigInteger amount) {
        final Function function = new Function(
                FUNC_DEPOSITASSET,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(asset),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<BigInteger> amountFilled(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce, String user, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(FUNC_AMOUNTFILLED,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce),
                new org.fisco.bcos.web3j.abi.datatypes.Address(user),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> changeFeeMake(BigInteger feeMake_) {
        final Function function = new Function(
                FUNC_CHANGEFEEMAKE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(feeMake_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void changeFeeMake(BigInteger feeMake_, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CHANGEFEEMAKE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(feeMake_)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String changeFeeMakeSeq(BigInteger feeMake_) {
        final Function function = new Function(
                FUNC_CHANGEFEEMAKE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(feeMake_)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<BigInteger> feeMake() {
        final Function function = new Function(FUNC_FEEMAKE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> changeFeeRebate(BigInteger feeRebate_) {
        final Function function = new Function(
                FUNC_CHANGEFEEREBATE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(feeRebate_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void changeFeeRebate(BigInteger feeRebate_, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CHANGEFEEREBATE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(feeRebate_)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String changeFeeRebateSeq(BigInteger feeRebate_) {
        final Function function = new Function(
                FUNC_CHANGEFEEREBATE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(feeRebate_)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<String> feeAccount() {
        final Function function = new Function(FUNC_FEEACCOUNT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> changeFeeAccount(String feeAccount_) {
        final Function function = new Function(
                FUNC_CHANGEFEEACCOUNT,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(feeAccount_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void changeFeeAccount(String feeAccount_, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CHANGEFEEACCOUNT,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(feeAccount_)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String changeFeeAccountSeq(String feeAccount_) {
        final Function function = new Function(
                FUNC_CHANGEFEEACCOUNT,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(feeAccount_)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<BigInteger> feeRebate() {
        final Function function = new Function(FUNC_FEEREBATE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> changeFeeTake(BigInteger feeTake_) {
        final Function function = new Function(
                FUNC_CHANGEFEETAKE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(feeTake_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void changeFeeTake(BigInteger feeTake_, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CHANGEFEETAKE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(feeTake_)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String changeFeeTakeSeq(BigInteger feeTake_) {
        final Function function = new Function(
                FUNC_CHANGEFEETAKE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(feeTake_)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> changeAdmin(String admin_) {
        final Function function = new Function(
                FUNC_CHANGEADMIN,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(admin_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void changeAdmin(String admin_, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CHANGEADMIN,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(admin_)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String changeAdminSeq(String admin_) {
        final Function function = new Function(
                FUNC_CHANGEADMIN,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(admin_)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Boolean> orders(String param0, byte[] param1) {
        final Function function = new Function(FUNC_ORDERS,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(param0),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> feeTake() {
        final Function function = new Function(FUNC_FEETAKE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> changeAccountLevelsAddr(String accountLevelsAddr_) {
        final Function function = new Function(
                FUNC_CHANGEACCOUNTLEVELSADDR,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(accountLevelsAddr_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void changeAccountLevelsAddr(String accountLevelsAddr_, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CHANGEACCOUNTLEVELSADDR,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(accountLevelsAddr_)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String changeAccountLevelsAddrSeq(String accountLevelsAddr_) {
        final Function function = new Function(
                FUNC_CHANGEACCOUNTLEVELSADDR,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(accountLevelsAddr_)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<String> accountLevelsAddr() {
        final Function function = new Function(FUNC_ACCOUNTLEVELSADDR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> balanceOf(String asset, String user) {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(asset),
                new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> admin() {
        final Function function = new Function(FUNC_ADMIN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> availableVolume(String assetGet, BigInteger amountGet, String assetGive, BigInteger amountGive, BigInteger expires, BigInteger nonce, String user, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(FUNC_AVAILABLEVOLUME,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(assetGet),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGet),
                new org.fisco.bcos.web3j.abi.datatypes.Address(assetGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amountGive),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(expires),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nonce),
                new org.fisco.bcos.web3j.abi.datatypes.Address(user),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<OrderEventResponse> getOrderEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ORDER_EVENT, transactionReceipt);
        ArrayList<OrderEventResponse> responses = new ArrayList<OrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderEventResponse typedResponse = new OrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.assetGet = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.assetGive = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.expires = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.nonce = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(6).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OrderEventResponse> orderEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, OrderEventResponse>() {
            @Override
            public OrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ORDER_EVENT, log);
                OrderEventResponse typedResponse = new OrderEventResponse();
                typedResponse.log = log;
                typedResponse.assetGet = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.assetGive = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.expires = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.nonce = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OrderEventResponse> orderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDER_EVENT));
        return orderEventFlowable(filter);
    }

    public List<CancelEventResponse> getCancelEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CANCEL_EVENT, transactionReceipt);
        ArrayList<CancelEventResponse> responses = new ArrayList<CancelEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CancelEventResponse typedResponse = new CancelEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.assetGet = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.assetGive = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.expires = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.nonce = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.v = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
            typedResponse.r = (byte[]) eventValues.getNonIndexedValues().get(8).getValue();
            typedResponse.s = (byte[]) eventValues.getNonIndexedValues().get(9).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CancelEventResponse> cancelEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, CancelEventResponse>() {
            @Override
            public CancelEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CANCEL_EVENT, log);
                CancelEventResponse typedResponse = new CancelEventResponse();
                typedResponse.log = log;
                typedResponse.assetGet = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.assetGive = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.expires = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.nonce = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(6).getValue();
                typedResponse.v = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
                typedResponse.r = (byte[]) eventValues.getNonIndexedValues().get(8).getValue();
                typedResponse.s = (byte[]) eventValues.getNonIndexedValues().get(9).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CancelEventResponse> cancelEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANCEL_EVENT));
        return cancelEventFlowable(filter);
    }

    public List<TradeEventResponse> getTradeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRADE_EVENT, transactionReceipt);
        ArrayList<TradeEventResponse> responses = new ArrayList<TradeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TradeEventResponse typedResponse = new TradeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.assetGet = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.assetGive = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.get = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.give = (String) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TradeEventResponse> tradeEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, TradeEventResponse>() {
            @Override
            public TradeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRADE_EVENT, log);
                TradeEventResponse typedResponse = new TradeEventResponse();
                typedResponse.log = log;
                typedResponse.assetGet = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.assetGive = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.get = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.give = (String) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TradeEventResponse> tradeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRADE_EVENT));
        return tradeEventFlowable(filter);
    }

    public List<DepositEventResponse> getDepositEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSIT_EVENT, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<DepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.asset = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DepositEventResponse> depositEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, DepositEventResponse>() {
            @Override
            public DepositEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSIT_EVENT, log);
                DepositEventResponse typedResponse = new DepositEventResponse();
                typedResponse.log = log;
                typedResponse.asset = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositEventResponse> depositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSIT_EVENT));
        return depositEventFlowable(filter);
    }

    public List<WithdrawEventResponse> getWithdrawEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAW_EVENT, transactionReceipt);
        ArrayList<WithdrawEventResponse> responses = new ArrayList<WithdrawEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawEventResponse typedResponse = new WithdrawEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.asset = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, WithdrawEventResponse>() {
            @Override
            public WithdrawEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAW_EVENT, log);
                WithdrawEventResponse typedResponse = new WithdrawEventResponse();
                typedResponse.log = log;
                typedResponse.asset = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAW_EVENT));
        return withdrawEventFlowable(filter);
    }

    @Deprecated
    public static Exchange load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Exchange(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Exchange load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Exchange(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Exchange load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Exchange(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Exchange load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Exchange(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Exchange> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String admin_) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(admin_)));
        return deployRemoteCall(Exchange.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Exchange> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String admin_) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(admin_)));
        return deployRemoteCall(Exchange.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Exchange> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String admin_) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(admin_)));
        return deployRemoteCall(Exchange.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Exchange> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String admin_) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(admin_)));
        return deployRemoteCall(Exchange.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class OrderEventResponse {
        public Log log;

        public String assetGet;

        public BigInteger amountGet;

        public String assetGive;

        public BigInteger amountGive;

        public BigInteger expires;

        public BigInteger nonce;

        public String user;
    }

    public static class CancelEventResponse {
        public Log log;

        public String assetGet;

        public BigInteger amountGet;

        public String assetGive;

        public BigInteger amountGive;

        public BigInteger expires;

        public BigInteger nonce;

        public String user;

        public BigInteger v;

        public byte[] r;

        public byte[] s;
    }

    public static class TradeEventResponse {
        public Log log;

        public String assetGet;

        public BigInteger amountGet;

        public String assetGive;

        public BigInteger amountGive;

        public String get;

        public String give;
    }

    public static class DepositEventResponse {
        public Log log;

        public String asset;

        public String user;

        public BigInteger amount;

        public BigInteger balance;
    }

    public static class WithdrawEventResponse {
        public Log log;

        public String asset;

        public String user;

        public BigInteger amount;

        public BigInteger balance;
    }
}
