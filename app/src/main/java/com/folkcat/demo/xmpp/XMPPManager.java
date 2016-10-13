package com.folkcat.demo.xmpp;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smack.util.StringUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import static org.jivesoftware.smackx.filetransfer.FileTransfer.Error.connection;

/**
 * Created by Tamas on 2016/9/18.
 */
public class XMPPManager {
    private static final String TAG="XMPPManager";

    private XMPPConnection mXMPPConnection;
    public boolean conServer() {
        Log.i(TAG,"conServer started");
        ConnectionConfiguration config = new ConnectionConfiguration(
                "192.168.1.106", 5222);
        /** 是否启用安全验证 */
        config.setSASLAuthenticationEnabled(false);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        /** 是否启用调试 */
        // config.setDebuggerEnabled(true);
        /** 创建connection链接 */
        try {
            mXMPPConnection = new XMPPConnection(config);
            /** 建立连接 */
            mXMPPConnection.connect();
            Log.i(TAG,"连接成功了哦");
            return true;
        } catch (XMPPException e) {
            Log.i(TAG,"exception happend");
            e.printStackTrace();
        }
        return false;
    }
    public String register(String account, String password) {
        if (mXMPPConnection == null)
            return "0";
        Registration reg = new Registration();
        reg.setType(IQ.Type.SET);
        reg.setTo(mXMPPConnection.getServiceName());
        reg.setUsername(account);// 注意这里createAccount注册时，参数是username，不是jid，是“@”前面的部分。
        reg.setPassword(password);
        reg.addAttribute("android", "geolo_createUser_android");// 这边addAttribute不能为空，否则出错。所以做个标志是android手机创建的吧！！！！！
        PacketFilter filter = new AndFilter(new PacketIDFilter(
                reg.getPacketID()), new PacketTypeFilter(IQ.class));
        PacketCollector collector = mXMPPConnection.createPacketCollector(filter);
        mXMPPConnection.sendPacket(reg);
        IQ result = (IQ) collector.nextResult(SmackConfiguration
                .getPacketReplyTimeout());
        // Stop queuing results
        collector.cancel();// 停止请求results（是否成功的结果）
        if (result == null) {
            Log.e("RegistActivity", "No response from server.");
            return "0";
        } else if (result.getType() == IQ.Type.RESULT) {
            return "1";
        } else { // if (result.getType() == IQ.Type.ERROR)
            if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
                Log.e("RegistActivity", "IQ.Type.ERROR: "
                        + result.getError().toString());
                return "2";
            } else {
                Log.e("RegistActivity", "IQ.Type.ERROR: "
                        + result.getError().toString());
                return "3";
            }
        }
    }
    public boolean login(String a, String p) {
        try {
            if (mXMPPConnection== null)return false;
            mXMPPConnection.login(a, p);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public  boolean changePassword(String pwd) {
        try {
            mXMPPConnection.getAccountManager().changePassword(pwd);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void setPresence(int code) {
        if (mXMPPConnection== null)
            return;
        Presence presence;
        switch (code) {
            case 0:
                presence = new Presence(Presence.Type.available);
                mXMPPConnection.sendPacket(presence);
                Log.v("state", "设置在线");
                break;
            case 1:
                presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.chat);
                mXMPPConnection.sendPacket(presence);
                Log.v("state", "设置Q我吧");
                System.out.println(presence.toXML());
                break;
            case 2:
                presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.dnd);
                mXMPPConnection.sendPacket(presence);
                Log.v("state", "设置忙碌");
                System.out.println(presence.toXML());
                break;
            case 3:
                presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.away);
                mXMPPConnection.sendPacket(presence);
                Log.v("state", "设置离开");
                System.out.println(presence.toXML());
                break;
            case 4:
                Roster roster = mXMPPConnection.getRoster();
                Collection<RosterEntry> entries = roster.getEntries();
                for (RosterEntry entry : entries) {
                    presence = new Presence(Presence.Type.unavailable);
                    presence.setPacketID(Packet.ID_NOT_AVAILABLE);
                    presence.setFrom(mXMPPConnection.getUser());
                    presence.setTo(entry.getUser());
                    mXMPPConnection.sendPacket(presence);
                    System.out.println(presence.toXML());
                }
                // 向同一用户的其他客户端发送隐身状态
                presence = new Presence(Presence.Type.unavailable);
                presence.setPacketID(Packet.ID_NOT_AVAILABLE);
                presence.setFrom(mXMPPConnection.getUser());
                presence.setTo(StringUtils.parseBareAddress(mXMPPConnection.getUser()));
                mXMPPConnection.sendPacket(presence);
                Log.v("state", "设置隐身");
                break;
            case 5:
                presence = new Presence(Presence.Type.unavailable);
                mXMPPConnection.sendPacket(presence);
                Log.v("state", "设置离线");
                break;
            default:
                break;
        }
    }
    public  boolean logout(){
        try {
            mXMPPConnection.getAccountManager().deleteAccount();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void startVNC() {
        try {
            Log.i(TAG,"flag 1");
            Runtime.getRuntime();
            Object localObject = Runtime.getRuntime().exec("/system/xbin/su");
            DataOutputStream localDataOutputStream = new DataOutputStream(((Process)localObject).getOutputStream());
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(((Process)localObject).getErrorStream()));
            Log.i(TAG,"flag 2");
            localObject = new BufferedReader(new InputStreamReader(((Process)localObject).getInputStream()));
            Log.i("startVnc", "error: " + localBufferedReader.readLine());
            //added by tamas start
            localDataOutputStream.writeBytes("chmod 555 androidvncserver\n");
            localDataOutputStream.flush();
            //added by tamas end
            localDataOutputStream.writeBytes("/system/bin/androidvncserver -k /dev/input/event0 -t /dev/input/event1");
            Log.i(TAG,"flag 3");
            localDataOutputStream.flush();
            localDataOutputStream.close();
            Log.i("startVnc", "error: " + localBufferedReader.readLine() + "    result:" + ((BufferedReader)localObject).readLine());
            Log.i(TAG,"flag 4");
            return;
        }catch (IOException localIOException) {
            Log.e(TAG,"开启VNC出错啦");
            localIOException.printStackTrace();
        }
    }

}
