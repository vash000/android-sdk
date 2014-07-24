package com.relayr.core.ble.device;


public enum Relayr_BLEDeviceType {
	WunderbarHTU,
	WunderbarGYRO,
	WunderbarLIGHT,
	WunderbarMIC,
	WunderbarBRIDG,
	WunderbarIR,
	WunderbarApp,
	Unknown;

	public static Relayr_BLEDeviceType getDeviceType(String deviceName) {
		if (deviceName != null) {
			if (deviceName.equals("WunderbarHTU")) {
				return WunderbarHTU;
			}
			if (deviceName.equals("WunderbarGYRO")) {
				return WunderbarGYRO;
			}
			if (deviceName.equals("WunderbarLIGHT")) {
				return WunderbarLIGHT;
			}
			if (deviceName.equals("WunderbarMIC")) {
				return WunderbarMIC;
			}
			if (deviceName.equals("WunderbarBRIDG")) {
				return WunderbarBRIDG;
			}
			if (deviceName.equals("WunderbarIR")) {
				return WunderbarIR;
			}
			if (deviceName.equals("WunderbarApp")) {
				return WunderbarApp;
			}
		}
		return Unknown;
	}
}
