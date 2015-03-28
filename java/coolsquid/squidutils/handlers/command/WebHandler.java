/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class WebHandler extends Thread {
	
	private ICommandSender sender;
	private String url;
	
	public WebHandler(ICommandSender sender, String url) {
		this.sender = sender;
		this.url = url;
	}
	
	@Override
	public void run() {
		String text = "";
		try {
			URL url = new URL(this.url);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			InputStream input = connection.getInputStream();
			
			BufferedReader r = new BufferedReader(new InputStreamReader(input));
			while (true) {
				String s = r.readLine();
				if (s == null) break;
				text = text + s;
			}
			r.close();
			sender.addChatMessage(new ChatComponentText(text));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}