package com.github.diegopacheco.lettuce.codecs.main;

import static com.github.diegopacheco.lettuce.codecs.CodecUtils.*;
import java.nio.ByteBuffer;

import com.github.diegopacheco.lettuce.codecs.CodecUtils;
import com.lambdaworks.redis.RedisAsyncConnection;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.codec.ByteArrayCodec;
import com.lambdaworks.redis.codec.RedisCodec;
import com.lambdaworks.redis.output.StatusOutput;
import com.lambdaworks.redis.protocol.CommandArgs;
import com.lambdaworks.redis.protocol.ProtocolKeyword;

public class MainLettuceCodesWirteAppend {
	
	static enum HP implements ProtocolKeyword {
			APPEND;
	    public final byte[] bytes;
		
	    private HP() {
	        //bytes = "HP.HAPPEND".getBytes(LettuceCharsets.ASCII);
	    	bytes = "HP.HAPPEND".getBytes();
	    }
	    @Override
	    public byte[] getBytes() {
	        return bytes;
	    }
	};
	
	public static void main(String[] args) throws Throwable {
		
		RedisCodec<byte[], byte[]> codec = new ByteArrayCodec();

		RedisClient client = RedisClient.create("redis://localhost:6379");
		RedisAsyncConnection<byte[], byte[]> connection = client.connectAsync(codec);
		
		RedisFuture<String> cmdAppend = connection.dispatch(HP.APPEND,new StatusOutput<>(codec),new CommandArgs<>(codec).add(encodeStr("myhash1")).add(encodeStr("age")).add(encodeLong(3L)));
		System.out.println("binary [myhash1] HP.APPEND " + cmdAppend.get());
		
		RedisFuture<String> cmdAppend2 = connection.dispatch(HP.APPEND,new StatusOutput<>(codec),new CommandArgs<>(codec).add("myhash2").add("age").add(3L));
		System.out.println("string [myhash2] HP.APPEND " + cmdAppend2.get());
		
		//RedisFuture<String> cmdAppend = connection.dispatch(HP.APPEND,new StatusOutput<>(codec),new CommandArgs<>(codec).add(encodeStr("myhash1")).add(encodeStr("age")).add(encodeLong(3L)));
		//System.out.println("string [myhash1] HP.APPEND " + cmdAppend.get());
		
		//RedisFuture<List<String>> cmdAppend2 = connection.eval("return redis.call('HP.HAPPEND', KEYS[1],'age','3')", ScriptOutputType.MULTI, encodeStr(new String("myhash2")));
		//System.out.println("string [myhash2] HP.APPEND " + cmdAppend2.get());
		
		byte[] result = connection.hget(encodeStr("myhash1"), encodeStr("age")).get();
		System.out.print("binary [myhash1] hget => " + result + " bytes: ");
		printByteArray(result);
		System.out.println("binary [myhash1] hget => " + decodeLong(result));
		//System.out.println("binary [myhash1] hget => " + CodecUtils.decodeStr(ByteBuffer.wrap(result)));
		
		result = connection.hget(encodeStr("myhash2"), encodeStr("age")).get();
		System.out.print("string [myhash2] hget => " + result + " bytes: ");
		printByteArray(result);
		System.out.println("string [myhash2] hget => " + CodecUtils.decodeStr(ByteBuffer.wrap(result)));
		
	}
}
