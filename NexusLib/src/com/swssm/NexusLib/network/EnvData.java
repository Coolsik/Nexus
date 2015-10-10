package com.swssm.NexusLib.network;

public class EnvData {
	public class JSON_KEY {
		public static final String JSON_FUNCTION_CODE = "function_code";
		public static final String JSON_CHANNEL_ID = "channel_id";
		public static final String JSON_MAC_ADDRESS = "mac_address";
		public static final String JSON_EVENT_CODE = "event_code";
		
		public static final String JSON_RESULT = "result";
		public static final String JSON_RETURN_TYPE = "return_type";
		public static final String JSON_CHANNEL_LIST = "channel_list";
		public static final String JSON_URL = "URL";
		public static final String JSON_IS_SUPERUSER = "isSuperUser";
		
		public static final String JSON_FOLDER_NAME = "folderName";
		public static final String JSON_FILE_NAME = "fileName";
		
		public static final String JSON_PARENT_DIR = "parentDir";
		public static final String JSON_PRESENT_DIR = "presentDir";
		public static final String JSON_FOLDER_LIST = "folderlist";
		public static final String JSON_FILE_LIST = "filelist";
		
		public static final String JSON_FILE_EXTENSION = "extension";
	}
	
	public class JSON_VALUE {
		public static final int REQUEST_CHANNEL_LIST = 1;
		public static final int REQUEST_CHANNEL_STREAMING_URL = 2;
		
		public static final int REQUEST_IPTV_STREAMING = 4;
		public static final int REQUEST_REMOTE_CONTROL_EVENT = 5;
		
		public static final int REQUEST_FILE_LIST = 7;
		public static final int REQUEST_FILE_STREAMING = 8;
		
		public static final int SUCCESS = 0;
		public static final int FAIL = 1;
	}
	
	public class EVENT_HANDLER {
		public final static int CHANNEL_LIST_COMPLETE = 100; 
		public final static int STREAMING_START = 101; 
		
		public final static int FILE_LIST_COMPLETE = 102;
	}
	
	public static final String[] CHANNEL_LIST = {"KBS1", "KBS2", "SEOULMBC", "SBS", "EBS_2"}; 

	public class REMOTE_EVENT {
		//Number
		public static final int NUMBER_1 = 0x03;
		public static final int NUMBER_2 = 0x04;
		public static final int NUMBER_3 = 0x05;
		public static final int NUMBER_4 = 0x06;
		public static final int NUMBER_5 = 0x07;
		public static final int NUMBER_6 = 0x08;
		public static final int NUMBER_7 = 0x09;
		public static final int NUMBER_8 = 0x0A;
		public static final int NUMBER_9 = 0x0B;
		public static final int NUMBER_0 = 0x0C;

		public static final int Asterisk = 0x4E;
		public static final int Sharp = 0x4F;
		
		public static final int VolumeUp = 0x1F;
		public static final int VolumeDown = 0x40;
		public static final int ChannelUp = 0x49;
		public static final int ChannelDown = 0x4A;
		public static final int OK = 0x13;
		
		public static final int SetopBoxPower = 0x00;
		public static final int Previous = 0x61;
		public static final int Exit = 0x62;
		public static final int Menu = 0x41;
		public static final int Mute = 0x18;
		public static final int Up = 0x11;
		public static final int Down = 0x15;
		public static final int Left = 0x12;
		public static final int Right = 0x14;
		
		public static final int Rewind = 0x65;
		//public static final int PlayPause = 0x60;
		public static final int PlayPause = ChannelUp;
		public static final int Stop = 0x63;
		public static final int FastForward = 0x64;
		
		public static final int Search = 0x74;
		public static final int Erase = 0x16;
		public static final int LanguageKey = 0x0D;
	}
}
