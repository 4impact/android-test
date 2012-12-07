#!/bin/bash

##################################################################
# This shell script unlocks the specified android emulator.
# If no emulator is specified, it'll just hope that there's only
# one.
# Specify a device name as the first parameter.
##################################################################


# - - - - - - - - - - - - - - - - - - - - - 
# Fail if we can't find adb. We need it
# - - - - - - - - - - - - - - - - - - - - - 
fail_if_adb_not_found() {
	#FAIL IF ADB IS NOT ATTACHED
	command -v adb >/dev/null 2>&1 || { echo >&2 "This script requires adb on the path.  Aborting."; exit 1; }
}

# - - - - - - - - - - - - - - - - - - - - - 
# This is the main method for unlocking a device
# What we do depends on whether or not they've
# specified a device
# - - - - - - - - - - - - - - - - - - - - -
unlock_device() {
	if [ -z "$DEVICE" ]
	then
		unlock_default_emulator
	else
		unlock_specified_device
	fi;
	exit 0
}

# - - - - - - - - - - - - - - - - - - - - - -
# The user has specified a device. Unlock it.
# - - - - - - - - - - - - - - - - - - - - - -
unlock_specified_device() {
	echo "#################################################"
	echo "# Unlocking device named $DEVICE."
	echo "#################################################"
	
	MATCHING_DEVICES=`adb devices | awk '{print $1}' | grep $DEVICE | wc -l`
	
	if [ "1" -eq "$MATCHING_DEVICES" ]
	then
		adb -s $DEVICE shell input keyevent 82
		echo "...done"
		exit 0;
	else
		echo "Device named '$DEVICE' not found.";
		echo "If the device is really running, you may have to restart your adb server with 'adb kill-server'";
		adb devices;
		exit -1;
	fi;
}

# - - - - - - - - - - - - - - - - - - - - - - - - -
# The punter has not specified a device. We'll
# Just go for pot luck...
# - - - - - - - - - - - - - - - - - - - - - - - - -
unlock_default_emulator() {
	echo "#################################################"
	echo "# Unlocking attached emulator"
	echo "#################################################"
	adb shell input keyevent 82
	echo "...done"
	exit 0;
}

# - - - - - - - - - - - - - - - - - - - - - - - - -
# This is the main execution for the script
# Fail if ADB isn't found, otherwise unlock the
# device.
# - - - - - - - - - - - - - - - - - - - - - - - - -

fail_if_adb_not_found
DEVICE=$1
unlock_device
