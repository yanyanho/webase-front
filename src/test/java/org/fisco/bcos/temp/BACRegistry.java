package org.fisco.bcos.temp;

import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
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
public class BACRegistry extends Contract {
    public static String BINARY = "608060405234801561001057600080fd5b506111f4806100206000396000f30060806040526004361061008e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680631fc528fb1461009357806329965a1d146100ff5780633d584063146101705780635df8122f146101f357806365ba36c114610256578063a77836f6146102ad578063aabbb8ca14610331578063b87c6fbf146103c2575b600080fd5b34801561009f57600080fd5b506100fd600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080357bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19169060200190929190505050610446565b005b34801561010b57600080fd5b5061016e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035600019169060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506105b8565b005b34801561017c57600080fd5b506101b1600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610a8f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101ff57600080fd5b50610254600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610b94565b005b34801561026257600080fd5b5061028f600480360381019080803590602001908201803590602001919091929391929390505050610d56565b60405180826000191660001916815260200191505060405180910390f35b3480156102b957600080fd5b50610317600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080357bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19169060200190929190505050610de9565b604051808215151515815260200191505060405180910390f35b34801561033d57600080fd5b50610380600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035600019169060200190929190505050610ed0565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156103ce57600080fd5b5061042c600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080357bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19169060200190929190505050610fc4565b604051808215151515815260200191505060405180910390f35b6104508282610de9565b61045b57600061045d565b815b6000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191660001916815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060006101000a81548160ff0219169083151502179055505050565b60008073ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff16146105f357836105f5565b335b90503373ffffffffffffffffffffffffffffffffffffffff1661061782610a8f565b73ffffffffffffffffffffffffffffffffffffffff161415156106a2576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600f8152602001807f4e6f7420746865206d616e61676572000000000000000000000000000000000081525060200191505060405180910390fd5b6106ab83611143565b151515610720576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f4d757374206e6f7420626520616e20424143313635206861736800000000000081525060200191505060405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415801561078957503373ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614155b156109945760405160200180807f42414352454749535445525f4143434550545f4d41474943000000000000000081525060180190506040516020818303038152906040526040518082805190602001908083835b60208310151561080357805182526020820191506020810190506020830392506107de565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019168273ffffffffffffffffffffffffffffffffffffffff1663249cb3fa85846040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018083600019166000191681526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200192505050602060405180830381600087803b1580156108df57600080fd5b505af11580156108f3573d6000803e3d6000fd5b505050506040513d602081101561090957600080fd5b810190808051906020019092919050505060001916141515610993576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f446f6573206e6f7420696d706c656d656e742074686520696e7465726661636581525060200191505060405180910390fd5b5b816000808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000856000191660001916815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff1683600019168273ffffffffffffffffffffffffffffffffffffffff167f93baa6efbd2244243bfee6ce4cfdd1d04fc4c0e9a786abd3a41313bd352db15360405160405180910390a450505050565b60008073ffffffffffffffffffffffffffffffffffffffff16600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415610b2c57819050610b8f565b600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505b919050565b3373ffffffffffffffffffffffffffffffffffffffff16610bb483610a8f565b73ffffffffffffffffffffffffffffffffffffffff16141515610c3f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600f8152602001807f4e6f7420746865206d616e61676572000000000000000000000000000000000081525060200191505060405180910390fd5b8173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610c785780610c7b565b60005b600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167f605c2dbf762e5f7d60a546d42e7205dcb1b011ebc62a61736a57c9089d3a435060405160405180910390a35050565b60008282604051602001808383808284378201915050925050506040516020818303038152906040526040518082805190602001908083835b602083101515610db45780518252602082019150602081019050602083039250610d8f565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020905092915050565b6000806000610e1b856301ffc9a77c010000000000000000000000000000000000000000000000000000000002611177565b80925081935050506000821480610e325750600081145b15610e405760009250610ec8565b610e6d8563ffffffff7c010000000000000000000000000000000000000000000000000000000002611177565b80925081935050506000821480610e85575060008114155b15610e935760009250610ec8565b610e9d8585611177565b8092508193505050600182148015610eb55750600181145b15610ec35760019250610ec8565b600092505b505092915050565b60008060008073ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff1614610f0e5784610f10565b335b9150610f1b84611143565b15610f4157839050610f2d8282610fc4565b610f38576000610f3a565b815b9250610fbc565b6000808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000856000191660001916815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1692505b505092915050565b6000600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060009054906101000a900460ff161515611079576110728383610de9565b905061113d565b8273ffffffffffffffffffffffffffffffffffffffff166000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000847bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191660001916815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161490505b92915050565b6000806001027bffffffffffffffffffffffffffffffffffffffffffffffffffffffff600102831660001916149050919050565b60008060006301ffc9a77c010000000000000000000000000000000000000000000000000000000002905060405181815284600482015260208160248389617530fa935080519250505092509290505600a165627a7a723058205ce04a5c430316417f4f1773f9fa359be151727d30e94e654779e57c2535e6a80029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"_contract\",\"type\":\"address\"},{\"name\":\"_interfaceId\",\"type\":\"bytes4\"}],\"name\":\"updateBAC165Cache\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_interfaceHash\",\"type\":\"bytes32\"},{\"name\":\"_implementer\",\"type\":\"address\"}],\"name\":\"setInterfaceImplementer\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_addr\",\"type\":\"address\"}],\"name\":\"getManager\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_newManager\",\"type\":\"address\"}],\"name\":\"setManager\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_interfaceName\",\"type\":\"string\"}],\"name\":\"interfaceHash\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"pure\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_contract\",\"type\":\"address\"},{\"name\":\"_interfaceId\",\"type\":\"bytes4\"}],\"name\":\"implementsBAC165InterfaceNoCache\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_interfaceHash\",\"type\":\"bytes32\"}],\"name\":\"getInterfaceImplementer\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_contract\",\"type\":\"address\"},{\"name\":\"_interfaceId\",\"type\":\"bytes4\"}],\"name\":\"implementsBAC165Interface\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"addr\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"interfaceHash\",\"type\":\"bytes32\"},{\"indexed\":true,\"name\":\"implementer\",\"type\":\"address\"}],\"name\":\"InterfaceImplementerSet\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"addr\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"newManager\",\"type\":\"address\"}],\"name\":\"ManagerChanged\",\"type\":\"event\"}]";

    public static final String FUNC_UPDATEBAC165CACHE = "updateBAC165Cache";

    public static final String FUNC_SETINTERFACEIMPLEMENTER = "setInterfaceImplementer";

    public static final String FUNC_GETMANAGER = "getManager";

    public static final String FUNC_SETMANAGER = "setManager";

    public static final String FUNC_INTERFACEHASH = "interfaceHash";

    public static final String FUNC_IMPLEMENTSBAC165INTERFACENOCACHE = "implementsBAC165InterfaceNoCache";

    public static final String FUNC_GETINTERFACEIMPLEMENTER = "getInterfaceImplementer";

    public static final String FUNC_IMPLEMENTSBAC165INTERFACE = "implementsBAC165Interface";

    public static final Event INTERFACEIMPLEMENTERSET_EVENT = new Event("InterfaceImplementerSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event MANAGERCHANGED_EVENT = new Event("ManagerChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected BACRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BACRegistry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BACRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BACRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> updateBAC165Cache(String _contract, byte[] _interfaceId) {
        final Function function = new Function(
                FUNC_UPDATEBAC165CACHE, 
                Arrays.<Type>asList(new Address(_contract),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes4(_interfaceId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateBAC165Cache(String _contract, byte[] _interfaceId, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATEBAC165CACHE,
                Arrays.<Type>asList(new Address(_contract),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes4(_interfaceId)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String updateBAC165CacheSeq(String _contract, byte[] _interfaceId) {
        final Function function = new Function(
                FUNC_UPDATEBAC165CACHE,
                Arrays.<Type>asList(new Address(_contract),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes4(_interfaceId)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> setInterfaceImplementer(String _addr, byte[] _interfaceHash, String _implementer) {
        final Function function = new Function(
                FUNC_SETINTERFACEIMPLEMENTER,
                Arrays.<Type>asList(new Address(_addr),
                new Bytes32(_interfaceHash),
                new Address(_implementer)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setInterfaceImplementer(String _addr, byte[] _interfaceHash, String _implementer, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETINTERFACEIMPLEMENTER,
                Arrays.<Type>asList(new Address(_addr),
                new Bytes32(_interfaceHash),
                new Address(_implementer)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setInterfaceImplementerSeq(String _addr, byte[] _interfaceHash, String _implementer) {
        final Function function = new Function(
                FUNC_SETINTERFACEIMPLEMENTER,
                Arrays.<Type>asList(new Address(_addr),
                new Bytes32(_interfaceHash),
                new Address(_implementer)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<String> getManager(String _addr) {
        final Function function = new Function(FUNC_GETMANAGER,
                Arrays.<Type>asList(new Address(_addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setManager(String _addr, String _newManager) {
        final Function function = new Function(
                FUNC_SETMANAGER,
                Arrays.<Type>asList(new Address(_addr),
                new Address(_newManager)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setManager(String _addr, String _newManager, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETMANAGER,
                Arrays.<Type>asList(new Address(_addr),
                new Address(_newManager)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setManagerSeq(String _addr, String _newManager) {
        final Function function = new Function(
                FUNC_SETMANAGER,
                Arrays.<Type>asList(new Address(_addr),
                new Address(_newManager)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<byte[]> interfaceHash(String _interfaceName) {
        final Function function = new Function(FUNC_INTERFACEHASH,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_interfaceName)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<Boolean> implementsBAC165InterfaceNoCache(String _contract, byte[] _interfaceId) {
        final Function function = new Function(FUNC_IMPLEMENTSBAC165INTERFACENOCACHE,
                Arrays.<Type>asList(new Address(_contract),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes4(_interfaceId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> getInterfaceImplementer(String _addr, byte[] _interfaceHash) {
        final Function function = new Function(FUNC_GETINTERFACEIMPLEMENTER,
                Arrays.<Type>asList(new Address(_addr),
                new Bytes32(_interfaceHash)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> implementsBAC165Interface(String _contract, byte[] _interfaceId) {
        final Function function = new Function(FUNC_IMPLEMENTSBAC165INTERFACE,
                Arrays.<Type>asList(new Address(_contract),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes4(_interfaceId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public List<InterfaceImplementerSetEventResponse> getInterfaceImplementerSetEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(INTERFACEIMPLEMENTERSET_EVENT, transactionReceipt);
        ArrayList<InterfaceImplementerSetEventResponse> responses = new ArrayList<InterfaceImplementerSetEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            InterfaceImplementerSetEventResponse typedResponse = new InterfaceImplementerSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.interfaceHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.implementer = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerInterfaceImplementerSetEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(INTERFACEIMPLEMENTERSET_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerInterfaceImplementerSetEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(INTERFACEIMPLEMENTERSET_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<ManagerChangedEventResponse> getManagerChangedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(MANAGERCHANGED_EVENT, transactionReceipt);
        ArrayList<ManagerChangedEventResponse> responses = new ArrayList<ManagerChangedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ManagerChangedEventResponse typedResponse = new ManagerChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newManager = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerManagerChangedEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(MANAGERCHANGED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerManagerChangedEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(MANAGERCHANGED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static BACRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BACRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BACRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BACRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BACRegistry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BACRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BACRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BACRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BACRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BACRegistry.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BACRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BACRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<BACRegistry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BACRegistry.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BACRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BACRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class InterfaceImplementerSetEventResponse {
        public Log log;

        public String addr;

        public byte[] interfaceHash;

        public String implementer;
    }

    public static class ManagerChangedEventResponse {
        public Log log;

        public String addr;

        public String newManager;
    }
}
