/*
 * PingRequest
 *
 * Version 1.0
 * Author: Benni
 *
 * Packet Typ, der nur eine generelle Serverantwort abfragt
 */

package uni.bombenstimmung.de.serverconnection.packages;

public class PingRequest {

	private long sendTime = System.currentTimeMillis();
	
	public long getSendTime() {
		return sendTime;
	}
	
}
