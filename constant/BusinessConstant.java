/**
 * 
 */
package capstone_project.av_service.constant;


public class BusinessConstant {
	public interface Company {
		public static final String BKAV = "BKAV";
		public static final String MCAFEE = "MCAFEE";
		public static final String CMC = "CMC";
		public static final String KASPERSKY = "KASPERSKY";
		public static final String MALWARE_BYTES = "MALWARE_BYTES";
		public static final String AV_SYSTEM = "AV_SYSTEM";

		public static final String[] COMPANY_LIST = {AV_SYSTEM, MALWARE_BYTES, KASPERSKY, CMC, MCAFEE, BKAV};
	}
}
