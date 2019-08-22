package org.fisco.bcos.temp;

import io.reactivex.Flowable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple9;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

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
public class HashedTimelockBAC001 extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506117a0806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063398a7a981461006757806363615149146101085780637249fbb61461015f578063e16c7d98146101a8575b600080fd5b34801561007357600080fd5b506100ea600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803560001916906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506102c1565b60405180826000191660001916815260200191505060405180910390f35b34801561011457600080fd5b5061014560048036038101908080356000191690602001909291908035600019169060200190929190505050610afc565b604051808215151515815260200191505060405180910390f35b34801561016b57600080fd5b5061018e60048036038101908080356000191690602001909291905050506110c5565b604051808215151515815260200191505060405180910390f35b3480156101b457600080fd5b506101d760048036038101908080356000191690602001909291905050506115bf565b604051808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001878152602001866000191660001916815260200185815260200184151515158152602001831515151581526020018260001916600019168152602001995050505050505050505060405180910390f35b600082338360008111151561033e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260188152602001807f617373657420616d6f756e74206d757374206265203e2030000000000000000081525060200191505060405180910390fd5b808373ffffffffffffffffffffffffffffffffffffffff1663dd62ed3e84306040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200192505050602060405180830381600087803b15801561040e57600080fd5b505af1158015610422573d6000803e3d6000fd5b505050506040513d602081101561043857600080fd5b8101908080519060200190929190505050101515156104e5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260218152602001807f617373657420616c6c6f77616e6365206d757374206265203e3d20616d6f756e81526020017f740000000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b864281111515610583576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001807f74696d656c6f636b2074696d65206d75737420626520696e207468652066757481526020017f757265000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b6002338b89898d8d604051602001808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c01000000000000000000000000028152601401848152602001836000191660001916815260200182815260200196505050505050506040516020818303038152906040526040518082805190602001908083835b6020831015156106bb5780518252602082019150602081019050602083039250610696565b6001836020036101000a0380198251168184511680821785525050505050509050019150506020604051808303816000865af11580156106ff573d6000803e3d6000fd5b5050506040513d602081101561071457600080fd5b81019080805190602001909291905050509450610730856116fe565b1561073a57600080fd5b8673ffffffffffffffffffffffffffffffffffffffff1663cd8e473b3330896040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200180602001828103825260008152602001602001945050505050600060405180830381600087803b15801561082557600080fd5b505af1158015610839573d6000803e3d6000fd5b50505050610120604051908101604052803373ffffffffffffffffffffffffffffffffffffffff1681526020018b73ffffffffffffffffffffffffffffffffffffffff1681526020018873ffffffffffffffffffffffffffffffffffffffff1681526020018781526020018a600019168152602001898152602001600015158152602001600015158152602001600060010260001916815250600080876000191660001916815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550606082015181600301556080820151816004019060001916905560a0820151816005015560c08201518160060160006101000a81548160ff02191690831515021790555060e08201518160060160016101000a81548160ff02191690831515021790555061010082015181600701906000191690559050508973ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1686600019167f126d29b204d32b727dd74e23177cb91a96ef9e12bee7b957974f14f7f02da6058a8a8e8e604051808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001848152602001836000191660001916815260200182815260200194505050505060405180910390a45050505095945050505050565b60008083610b09816116fe565b1515610b7d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260198152602001807f636f6e7472616374496420646f6573206e6f742065786973740000000000000081525060200191505060405180910390fd5b84846002816040516020018082600019166000191681526020019150506040516020818303038152906040526040518082805190602001908083835b602083101515610bde5780518252602082019150602081019050602083039250610bb9565b6001836020036101000a0380198251168184511680821785525050505050509050019150506020604051808303816000865af1158015610c22573d6000803e3d6000fd5b5050506040513d6020811015610c3757600080fd5b81019080805190602001909291905050506000191660008084600019166000191681526020019081526020016000206004015460001916141515610ce3576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f686173686c6f636b206861736820646f6573206e6f74206d617463680000000081525060200191505060405180910390fd5b863373ffffffffffffffffffffffffffffffffffffffff16600080836000191660001916815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610dc4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f776974686472617761626c653a206e6f7420726563656976657200000000000081525060200191505060405180910390fd5b60001515600080836000191660001916815260200190815260200160002060060160009054906101000a900460ff161515141515610e6a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601f8152602001807f776974686472617761626c653a20616c72656164792077697468647261776e0081525060200191505060405180910390fd5b42600080836000191660001916815260200190815260200160002060050154111515610f24576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260318152602001807f776974686472617761626c653a2074696d656c6f636b2074696d65206d75737481526020017f20626520696e207468652066757475726500000000000000000000000000000081525060400191505060405180910390fd5b600080896000191660001916815260200190815260200160002094508685600701816000191690555060018560060160006101000a81548160ff0219169083151502179055508460020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f49bb76b8660010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1687600301546040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828152602001806020018281038252600081526020016020019350505050600060405180830381600087803b15801561106d57600080fd5b505af1158015611081573d6000803e3d6000fd5b5050505087600019167f92bf50165897ae22641c940782c84e8198da134ab95148df06f897d68a2ae74360405160405180910390a260019550505050505092915050565b600080826110d2816116fe565b1515611146576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260198152602001807f636f6e7472616374496420646f6573206e6f742065786973740000000000000081525060200191505060405180910390fd5b833373ffffffffffffffffffffffffffffffffffffffff16600080836000191660001916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515611227576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f726566756e6461626c653a206e6f742073656e6465720000000000000000000081525060200191505060405180910390fd5b60001515600080836000191660001916815260200190815260200160002060060160019054906101000a900460ff1615151415156112cd576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f726566756e6461626c653a20616c726561647920726566756e6465640000000081525060200191505060405180910390fd5b60001515600080836000191660001916815260200190815260200160002060060160009054906101000a900460ff161515141515611373576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f726566756e6461626c653a20616c72656164792077697468647261776e00000081525060200191505060405180910390fd5b426000808360001916600019168152602001908152602001600020600501541115151561142e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001807f726566756e6461626c653a2074696d656c6f636b206e6f74207965742070617381526020017f736564000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b6000808660001916600019168152602001908152602001600020925060018360060160016101000a81548160ff0219169083151502179055508260020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f49bb76b8460000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1685600301546040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828152602001806020018281038252600081526020016020019350505050600060405180830381600087803b15801561156a57600080fd5b505af115801561157e573d6000803e3d6000fd5b5050505084600019167fb3d16fe3b0dbe874dc251948389f241d9aeeafe19b0055cd1fcd872bdc7882c860405160405180910390a260019350505050919050565b600080600080600080600080600080600015156115db8c6116fe565b1515141561161a5760008060008060008060008060008595508460010294508393508060010290509950995099509950995099509950995099506116f0565b6000808c6000191660001916815260200190815260200160002090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168260020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168360030154846004015485600501548660060160009054906101000a900460ff168760060160019054906101000a900460ff1688600701549950995099509950995099509950995099505b509193959799909294969850565b60008073ffffffffffffffffffffffffffffffffffffffff16600080846000191660001916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141590509190505600a165627a7a72305820ce8b47b9fd7caf1af17edd0f034d9710c70b377bcad68e422efd994a0afc0b690029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"_receiver\",\"type\":\"address\"},{\"name\":\"_hashlock\",\"type\":\"bytes32\"},{\"name\":\"_timelock\",\"type\":\"uint256\"},{\"name\":\"_assetContract\",\"type\":\"address\"},{\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"newContract\",\"outputs\":[{\"name\":\"contractId\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_contractId\",\"type\":\"bytes32\"},{\"name\":\"_preimage\",\"type\":\"bytes32\"}],\"name\":\"withdraw\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_contractId\",\"type\":\"bytes32\"}],\"name\":\"refund\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_contractId\",\"type\":\"bytes32\"}],\"name\":\"getContract\",\"outputs\":[{\"name\":\"sender\",\"type\":\"address\"},{\"name\":\"receiver\",\"type\":\"address\"},{\"name\":\"assetContract\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"},{\"name\":\"hashlock\",\"type\":\"bytes32\"},{\"name\":\"timelock\",\"type\":\"uint256\"},{\"name\":\"withdrawn\",\"type\":\"bool\"},{\"name\":\"refunded\",\"type\":\"bool\"},{\"name\":\"preimage\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"contractId\",\"type\":\"bytes32\"},{\"indexed\":true,\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"receiver\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"assetContract\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"hashlock\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"timelock\",\"type\":\"uint256\"}],\"name\":\"LogHTLCBAC001New\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"contractId\",\"type\":\"bytes32\"}],\"name\":\"LogHTLCBAC001Withdraw\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"contractId\",\"type\":\"bytes32\"}],\"name\":\"LogHTLCBAC001Refund\",\"type\":\"event\"}]";

    public static final String FUNC_NEWCONTRACT = "newContract";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_REFUND = "refund";

    public static final String FUNC_GETCONTRACT = "getContract";

    public static final Event LOGHTLCBAC001NEW_EVENT = new Event("LogHTLCBAC001New",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGHTLCBAC001WITHDRAW_EVENT = new Event("LogHTLCBAC001Withdraw",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event LOGHTLCBAC001REFUND_EVENT = new Event("LogHTLCBAC001Refund",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    @Deprecated
    protected HashedTimelockBAC001(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HashedTimelockBAC001(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HashedTimelockBAC001(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HashedTimelockBAC001(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> newContract(String _receiver, byte[] _hashlock, BigInteger _timelock, String _assetContract, BigInteger _amount) {
        final Function function = new Function(
                FUNC_NEWCONTRACT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_receiver),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_hashlock),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_timelock),
                new org.fisco.bcos.web3j.abi.datatypes.Address(_assetContract),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void newContract(String _receiver, byte[] _hashlock, BigInteger _timelock, String _assetContract, BigInteger _amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_NEWCONTRACT,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_receiver),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_hashlock),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_timelock),
                new org.fisco.bcos.web3j.abi.datatypes.Address(_assetContract),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String newContractSeq(String _receiver, byte[] _hashlock, BigInteger _timelock, String _assetContract, BigInteger _amount) {
        final Function function = new Function(
                FUNC_NEWCONTRACT,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_receiver),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_hashlock),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_timelock),
                new org.fisco.bcos.web3j.abi.datatypes.Address(_assetContract),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> withdraw(byte[] _contractId, byte[] _preimage) {
        final Function function = new Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_contractId),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_preimage)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void withdraw(byte[] _contractId, byte[] _preimage, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_contractId),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_preimage)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String withdrawSeq(byte[] _contractId, byte[] _preimage) {
        final Function function = new Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_contractId),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_preimage)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> refund(byte[] _contractId) {
        final Function function = new Function(
                FUNC_REFUND,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_contractId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void refund(byte[] _contractId, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REFUND,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_contractId)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String refundSeq(byte[] _contractId) {
        final Function function = new Function(
                FUNC_REFUND,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_contractId)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]>> getContract(byte[] _contractId) {
        final Function function = new Function(FUNC_GETCONTRACT,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_contractId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteCall<Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]>>(
                new Callable<Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]>>() {
                    @Override
                    public Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (byte[]) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (Boolean) results.get(6).getValue(),
                                (Boolean) results.get(7).getValue(),
                                (byte[]) results.get(8).getValue());
                    }
                });
    }

    public List<LogHTLCBAC001NewEventResponse> getLogHTLCBAC001NewEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGHTLCBAC001NEW_EVENT, transactionReceipt);
        ArrayList<LogHTLCBAC001NewEventResponse> responses = new ArrayList<LogHTLCBAC001NewEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogHTLCBAC001NewEventResponse typedResponse = new LogHTLCBAC001NewEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.receiver = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.assetContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.hashlock = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.timelock = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogHTLCBAC001NewEventResponse> logHTLCBAC001NewEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, LogHTLCBAC001NewEventResponse>() {
            @Override
            public LogHTLCBAC001NewEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGHTLCBAC001NEW_EVENT, log);
                LogHTLCBAC001NewEventResponse typedResponse = new LogHTLCBAC001NewEventResponse();
                typedResponse.log = log;
                typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.receiver = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.assetContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.hashlock = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.timelock = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogHTLCBAC001NewEventResponse> logHTLCBAC001NewEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGHTLCBAC001NEW_EVENT));
        return logHTLCBAC001NewEventFlowable(filter);
    }

    public List<LogHTLCBAC001WithdrawEventResponse> getLogHTLCBAC001WithdrawEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGHTLCBAC001WITHDRAW_EVENT, transactionReceipt);
        ArrayList<LogHTLCBAC001WithdrawEventResponse> responses = new ArrayList<LogHTLCBAC001WithdrawEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogHTLCBAC001WithdrawEventResponse typedResponse = new LogHTLCBAC001WithdrawEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogHTLCBAC001WithdrawEventResponse> logHTLCBAC001WithdrawEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, LogHTLCBAC001WithdrawEventResponse>() {
            @Override
            public LogHTLCBAC001WithdrawEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGHTLCBAC001WITHDRAW_EVENT, log);
                LogHTLCBAC001WithdrawEventResponse typedResponse = new LogHTLCBAC001WithdrawEventResponse();
                typedResponse.log = log;
                typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogHTLCBAC001WithdrawEventResponse> logHTLCBAC001WithdrawEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGHTLCBAC001WITHDRAW_EVENT));
        return logHTLCBAC001WithdrawEventFlowable(filter);
    }

    public List<LogHTLCBAC001RefundEventResponse> getLogHTLCBAC001RefundEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGHTLCBAC001REFUND_EVENT, transactionReceipt);
        ArrayList<LogHTLCBAC001RefundEventResponse> responses = new ArrayList<LogHTLCBAC001RefundEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogHTLCBAC001RefundEventResponse typedResponse = new LogHTLCBAC001RefundEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogHTLCBAC001RefundEventResponse> logHTLCBAC001RefundEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, LogHTLCBAC001RefundEventResponse>() {
            @Override
            public LogHTLCBAC001RefundEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGHTLCBAC001REFUND_EVENT, log);
                LogHTLCBAC001RefundEventResponse typedResponse = new LogHTLCBAC001RefundEventResponse();
                typedResponse.log = log;
                typedResponse.contractId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogHTLCBAC001RefundEventResponse> logHTLCBAC001RefundEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGHTLCBAC001REFUND_EVENT));
        return logHTLCBAC001RefundEventFlowable(filter);
    }

    @Deprecated
    public static HashedTimelockBAC001 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HashedTimelockBAC001(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HashedTimelockBAC001 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HashedTimelockBAC001(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HashedTimelockBAC001 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HashedTimelockBAC001(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HashedTimelockBAC001 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HashedTimelockBAC001(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HashedTimelockBAC001> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HashedTimelockBAC001.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<HashedTimelockBAC001> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HashedTimelockBAC001.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<HashedTimelockBAC001> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HashedTimelockBAC001.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<HashedTimelockBAC001> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HashedTimelockBAC001.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class LogHTLCBAC001NewEventResponse {
        public Log log;

        public byte[] contractId;

        public String sender;

        public String receiver;

        public String assetContract;

        public BigInteger amount;

        public byte[] hashlock;

        public BigInteger timelock;
    }

    public static class LogHTLCBAC001WithdrawEventResponse {
        public Log log;

        public byte[] contractId;
    }

    public static class LogHTLCBAC001RefundEventResponse {
        public Log log;

        public byte[] contractId;
    }
}
