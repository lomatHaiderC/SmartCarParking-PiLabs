import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingServer extends LoginSession {
	static int port, commaCount = 0;
	ServerSocket server = null;
	static Socket client = null;
	static String rfid;
	static LocalDateTime now;
	static BufferedReader cin;
	static PrintStream cout;
	static String[] splitRequest;
	static String nowDate, res = "0";
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public ParkingServer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		now = LocalDateTime.now();
		nowDate = dtf.format(now);
		ParkingServer parkingserver = new ParkingServer();
		IpPortConfiguration ipConfig = new IpPortConfiguration();
		port = ipConfig.getPortNumber();
		parkingserver.startAction();
	}

	private void startAction() {
		// TODO Auto-generated method stub
		try {
			server = new ServerSocket(port);
			while (true) {
				client = server.accept();
				cin = new BufferedReader(new InputStreamReader(client.getInputStream()));

				cout = new PrintStream(client.getOutputStream());
				if (client.isConnected()) {

					rfid = cin.readLine();

					System.out.println("RFID" + rfid);
					splitRequest = rfid.split("-");
					if (splitRequest[0].equals("Entry")) {
						ServerRFIDBooth serverbooth = new ServerRFIDBooth();
						res = serverbooth.rfidbooth(rfid);
					} 
					else if (splitRequest[0].equals("New VIP User Entry")) {
						ServerInsertVIP serverVip = new ServerInsertVIP();
						res = serverVip.insertVIPMember(rfid);
					} 
					else if (splitRequest[0].equals("Login")) {
						ServerLogin serverLog = new ServerLogin();
						res = serverLog.loginUser(rfid);
					} 
					else if (splitRequest[0].equals("Forget Pass")) {
						ServerForgetPass serverforget = new ServerForgetPass();
						res = serverforget.getPass(rfid);
					} 
					else if (splitRequest[0].equals("validity")) {
						ServerValidity servervalidity = new ServerValidity();
						res = servervalidity.getInfo(rfid);
					} 
					else if (splitRequest[0].equals("barrierGate")) {
						ServerBarrierGateOpen serverbarrier = new ServerBarrierGateOpen();
						res = serverbarrier.getInfo(rfid);
					} 
					else if (splitRequest[0].equals("FareType")) {
						ServerFareType serverFareType = new ServerFareType();
						res = serverFareType.getInfo(rfid);
					} 
					else if (splitRequest[0].equals("Card Check")) {
						ServerCardCheck sck = new ServerCardCheck();
						res = sck.cardCheck(rfid);
					} 
					else if (splitRequest[0].equals("Payment")) {
						ServerPayment serverPayment = new ServerPayment();
						res = serverPayment.payment(rfid);
					} 
					else if (splitRequest[0].equals("VIP Card Insert")) {
						ServerCard serverCard = new ServerCard();
						res = serverCard.insert(rfid);
					} 
					else if (splitRequest[0].equals("Normal Card Insert")) {
						ServerCard serverCard = new ServerCard();
						res = serverCard.insertNormal(rfid);
					}
					else if (splitRequest[0].equals("Exit")) {
						res = checkCard(rfid);
						if (res.equalsIgnoreCase("Vip")) {
							ServerVIPExit serverVipExit = new ServerVIPExit();
							res = serverVipExit.vipExit(rfid);
						} 
						else if (res.equalsIgnoreCase("Normal")) {
							ServerNormalExit snExit = new ServerNormalExit();
							res = snExit.exit(rfid);
						} 
						else {
							res = "Not Found";
						}
					} 
					else if (splitRequest[0].equals("payment done")) {
						ServerPayment serverPayment = new ServerPayment();
						res = serverPayment.paymentdone(rfid);
					} 					
					else if(splitRequest[0].equals("barrierGate")) {
						ServerBarrierGateOpen serverbarrier=new ServerBarrierGateOpen();
						res=serverbarrier.getInfo(rfid);
					}
					else if (splitRequest[0].equals("vip post payment done")) {
						ServerVipPayment servervip = new ServerVipPayment();
						res = servervip.vippostpaymentdone(rfid);
					} 
					else if (splitRequest[0].equals("vip pre payment done")) {
						ServerVipPayment servervip = new ServerVipPayment();
						res = servervip.vipprepaymentdone(rfid);
					} 
					else if (splitRequest[0].equals("postpaid payment")) {
						ServerVipPayment servervip = new ServerVipPayment();
						res = servervip.postvippayment(rfid);
					} 
					else if (splitRequest[0].equals("prepaid payment")) {
						ServerVipPayment servervip = new ServerVipPayment();
						res = servervip.prepaidpayment(rfid);
					} 
					else if (splitRequest[0].equals("vipinfo")) {
						ServerVipInfo serverInfo = new ServerVipInfo();
						res = serverInfo.vipinfo();
					} 
					else if (splitRequest[0].equals("vipinfoPrepaid")) {
						ServerVipInfo serverInfo = new ServerVipInfo();
						res = serverInfo.vipinfoPrepaid();
					} 
					else if (splitRequest[0].equals("vipinfoPostpaid")) {
						ServerVipInfo serverInfo = new ServerVipInfo();
						res = serverInfo.vipinfoPostpaid();
					}  
					else if (splitRequest[0].equals("totalincome")) {
						serverpaymentreport serverInfo = new serverpaymentreport();
						res = serverInfo.totalincome(rfid);
					}  
					else if (splitRequest[0].equals("normalincome")) {
						serverpaymentreport serverInfo = new serverpaymentreport();
						res = serverInfo.normalincome(rfid);
					}  
					else if (splitRequest[0].equals("vipincome")) {
						serverpaymentreport serverInfo = new serverpaymentreport();
						res = serverInfo.vipincome(rfid);
					} 
					else if (splitRequest[0].equals("Create new Admin")) {
						ServerCreateAdmin scAdmin = new ServerCreateAdmin();
						res = scAdmin.createAccount(rfid);
					} 
					else if (splitRequest[0].equals("Create New Employee")) {
						ServerCreateEmployee scEmployee = new ServerCreateEmployee();
						res = scEmployee.createAccount(rfid);
					}
					else if (splitRequest[0].equals("Pass Change Admin")) {
						ServerPassChange scAdmin = new ServerPassChange();
						res = scAdmin.changeAdmin(rfid);
					}
					else if (splitRequest[0].equals("Pass Change Employee")) {
						ServerPassChange scAdmin = new ServerPassChange();
						res = scAdmin.changeEmployee(rfid);
					}
					cout.println(res);
					cin.close();
					client.close();
					cout.close();
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String checkCard(String rfid) {
		String returnMsg = null;
		String[] card = rfid.split(",");
		String sncard = card[2];
		ResultSet rsCard, rsCard1;
		PreparedStatement stmtCard, stmtCard1;
		String queryCard, queryCard1;
		queryCard = "SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`=? and isParked=' 1 ' ";
		queryCard1 = "SELECT * FROM `rfid` WHERE `generateRFID_NO`=? and isParked=' 1 ' ";
		try {
			stmtCard = MyConnection.getConnection().prepareStatement(queryCard);

			stmtCard1 = MyConnection.getConnection().prepareStatement(queryCard1);
			stmtCard.setString(1, sncard);
			stmtCard1.setString(1, sncard);
			rsCard = stmtCard.executeQuery();
			rsCard1 = stmtCard1.executeQuery();
			if (rsCard.next()) {
				returnMsg = "VIP";
			} else if (rsCard1.next()) {
				returnMsg = "Normal";
			} else {
				returnMsg = "Not Found";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnMsg;
	}

}
