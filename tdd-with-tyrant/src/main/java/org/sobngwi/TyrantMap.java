/**
 * 
 */
package org.sobngwi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;

/**
 * @author Alain Narcisse SOBNGWI
 *
 */
public class TyrantMap implements Iterable <byte[]>{

	
	
	private Socket socket;
	private DataOutputStream writer;
	private DataInputStream reader;

	public void put( byte[] key, byte[] value) throws  IOException {

		writeOperationCode(OperationCode.PUT_OPERATION.getCode());

		writeKeyValueDatas(key, value);
		
		int status = reader.read();
		if ( status != 0 ) {
			throw new RuntimeException(" PUT : insertion Failed ");
		}
		return ;
	}

	

	public byte[] get(byte[] key) throws IOException {

		writeOperationCode(OperationCode.GET_OPERATION.getCode());
		
		writekeyDatas(key);
		
		return readBytes();
	}
	
	public void clear() throws IOException {

		writeOperationCode(OperationCode.VANISH_OPERATION.getCode());

		int status = reader.read();
		if ( status != 0 ) {
			throw new RuntimeException(" CLEAR : insertion Failed ");
		}
	}
	
	public void remove(byte[] key) throws IOException {
		if ( key == null )
			throw new IllegalArgumentException("key shold not be Null !") ;
		writeOperationCode(OperationCode.REMOVE_OPERATION.getCode());
		writekeyDatas(key);

		int status = reader.read();
		if ( status == 1 ) 
			return ;
		if ( status != 0 ) 
			throw new RuntimeException(" READ : insertion Failed ");
		
	}

	public long size() throws IOException {

		writeOperationCode(OperationCode.SIZE_OPERATION.getCode());

		int status = reader.read();
		if ( status != 0 ) {
			throw new RuntimeException(" CLEAR : insertion Failed ");
		}
		return reader.readLong();
	}

	@Override
	public Iterator<byte[]> iterator() {
		try {
			reset();
			byte[] firstKey = getNextKey();

		return new Iterator<byte[]>(){
			byte[]  nextKey = firstKey ;
			@Override
			public boolean hasNext() {
				return nextKey != null ;
			}

			@Override
			public byte[] next() {
				byte[]  result ;
				try {
					result = get(nextKey) ;
					nextKey = getNextKey();
				} catch (IOException e) {
					throw new RuntimeException(e) ;
				}
				return result;
			}
		} ;
		} catch (IOException e) {
			throw new RuntimeException(e) ;
		}
		
	}

	public byte[] getNextKey() throws IOException{

		writeOperationCode(OperationCode.NEXTKEY_OPERATION.getCode());

		return readBytes();
	
	}

	public void openConnection() throws UnknownHostException, IOException {
		socket = new Socket ( "localhost" , 1978);
		writer = new DataOutputStream(socket.getOutputStream());
		reader = new DataInputStream(socket.getInputStream());
	}

	public void closeConnection() throws IOException {
		socket.close();
	}
	
	private void writekeyDatas(byte[] key) throws IOException {
		writer.writeInt(key.length);
		writer.write(key);
	}
	
	private void writeKeyValueDatas(byte[] key, byte[] value)
			throws IOException {
		writer.writeInt(key.length);
		writer.writeInt(value.length);
		writer.write(key);
		writer.write(value);
	}
	
	private void writeOperationCode(int operation) throws IOException {
		writer.write(OperationCode.OPERATION_PREFIX.getCode());
		writer.write(operation);
	}

	private byte[] readBytes() throws IOException {
		int status = reader.read();
		if ( status == 1 ) 
			return null ;
		if ( status != 0 ) 
			throw new RuntimeException(" GET NEXT KEY :  Failed ");
		
		int length = reader.readInt();
		byte[] results= new  byte[length] ;
		reader.read(results) ; // TODO read longer values
		return results;
	}
	
	private void reset() throws IOException {

		writeOperationCode(OperationCode.RESET_OPERATION.getCode());

		int status = reader.read();
		if ( status != 0 ) {
			throw new RuntimeException(" RESET  :  Failed ");
		}
	
	}

}
