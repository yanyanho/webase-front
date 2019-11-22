package com.webank.webase.front.trade.raiden;

import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
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
public class SecretRegistry extends Contract {
    public static String BINARY = "608060405234801561001057600080fd5b50610379806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806312ad8bfc14610046578063bbe8a9b61461008c578063c1f629461461015c575b600080fd5b6100726004803603602081101561005c57600080fd5b810190808035906020019092919050505061019e565b604051808215151515815260200191505060405180910390f35b610142600480360360208110156100a257600080fd5b81019080803590602001906401000000008111156100bf57600080fd5b8201836020820111156100d157600080fd5b803590602001918460208302840111640100000000831117156100f357600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192905050506102df565b604051808215151515815260200191505060405180910390f35b6101886004803603602081101561017257600080fd5b8101908080359060200190929190505050610331565b6040518082815260200191505060405180910390f35b600080600283604051602001808281526020019150506040516020818303038152906040526040518082805190602001908083835b602083106101f657805182526020820191506020810190506020830392506101d3565b6001836020036101000a038019825116818451168082178552505050505050905001915050602060405180830381855afa158015610238573d6000803e3d6000fd5b5050506040513d602081101561024d57600080fd5b8101908080519060200190929190505050905060008060008381526020019081526020016000205411156102855760009150506102da565b4360008083815260200190815260200160002081905550807fc8ee7ba45d0c5351df845eda156d523bd6865844a5f2c69df35b757e2f794fa1846040518082815260200191505060405180910390a260019150505b919050565b6000806001905060008090505b83518110156103275761031184828151811061030457fe5b602002602001015161019e565b61031a57600091505b80806001019150506102ec565b5080915050919050565b600080600083815260200190815260200160002054905091905056fea165627a7a72305820b6024d5388b398e9f924b8f0efec6cc706c450aa817c1c92ce58ba62a0b412410029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"secret\",\"type\":\"bytes32\"}],\"name\":\"registerSecret\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"secrets\",\"type\":\"bytes32[]\"}],\"name\":\"registerSecretBatch\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"secrethash\",\"type\":\"bytes32\"}],\"name\":\"getSecretRevealBlockHeight\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"secrethash\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"secret\",\"type\":\"bytes32\"}],\"name\":\"SecretRevealed\",\"type\":\"event\"}]";

    public static final String FUNC_REGISTERSECRET = "registerSecret";

    public static final String FUNC_REGISTERSECRETBATCH = "registerSecretBatch";

    public static final String FUNC_GETSECRETREVEALBLOCKHEIGHT = "getSecretRevealBlockHeight";

    public static final Event SECRETREVEALED_EVENT = new Event("SecretRevealed",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    @Deprecated
    protected SecretRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SecretRegistry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SecretRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SecretRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> registerSecret(byte[] secret) {
        final Function function = new Function(
                FUNC_REGISTERSECRET, 
                Arrays.<Type>asList(new Bytes32(secret)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void registerSecret(byte[] secret, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERSECRET,
                Arrays.<Type>asList(new Bytes32(secret)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String registerSecretSeq(byte[] secret) {
        final Function function = new Function(
                FUNC_REGISTERSECRET,
                Arrays.<Type>asList(new Bytes32(secret)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> registerSecretBatch(List<byte[]> secrets) {
        final Function function = new Function(
                FUNC_REGISTERSECRETBATCH,
                Arrays.<Type>asList(secrets.isEmpty()?org.fisco.bcos.web3j.abi.datatypes.DynamicArray.empty("bytes32[]"):new org.fisco.bcos.web3j.abi.datatypes.DynamicArray<Bytes32>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(secrets, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void registerSecretBatch(List<byte[]> secrets, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERSECRETBATCH,
                Arrays.<Type>asList(secrets.isEmpty()?org.fisco.bcos.web3j.abi.datatypes.DynamicArray.empty("bytes32[]"):new org.fisco.bcos.web3j.abi.datatypes.DynamicArray<Bytes32>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(secrets, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String registerSecretBatchSeq(List<byte[]> secrets) {
        final Function function = new Function(
                FUNC_REGISTERSECRETBATCH,
                Arrays.<Type>asList(secrets.isEmpty()?org.fisco.bcos.web3j.abi.datatypes.DynamicArray.empty("bytes32[]"):new org.fisco.bcos.web3j.abi.datatypes.DynamicArray<Bytes32>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(secrets, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<BigInteger> getSecretRevealBlockHeight(byte[] secrethash) {
        final Function function = new Function(FUNC_GETSECRETREVEALBLOCKHEIGHT,
                Arrays.<Type>asList(new Bytes32(secrethash)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<SecretRevealedEventResponse> getSecretRevealedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SECRETREVEALED_EVENT, transactionReceipt);
        ArrayList<SecretRevealedEventResponse> responses = new ArrayList<SecretRevealedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SecretRevealedEventResponse typedResponse = new SecretRevealedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.secrethash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.secret = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerSecretRevealedEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(SECRETREVEALED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerSecretRevealedEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(SECRETREVEALED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static SecretRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SecretRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SecretRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SecretRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SecretRegistry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SecretRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SecretRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SecretRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SecretRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SecretRegistry.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SecretRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SecretRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SecretRegistry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SecretRegistry.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SecretRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SecretRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class SecretRevealedEventResponse {
        public Log log;

        public byte[] secrethash;

        public byte[] secret;
    }
}
