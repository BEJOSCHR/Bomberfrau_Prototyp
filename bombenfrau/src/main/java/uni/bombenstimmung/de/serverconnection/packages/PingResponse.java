/*
 * PingResponse
 *
 * Version 1.0
 * Author: Benni
 *
 * Packet Typ, der eine generelle Serverantwort darstellt
 */

package uni.bombenstimmung.de.serverconnection.packages;

public class PingResponse {

	private long originalSendTime = -1;
	
	public PingResponse() { }
	public PingResponse(long originalSendTime) {
		
		this.originalSendTime = originalSendTime;
		
	}
	
	public long getOriginalSendTime() {
		return originalSendTime;
	}
	
}
