#!/bin/bash

DEVICE=$1

# - - - - - - - - - - - - - - - - - - - - - - - -
# Exit if they haven't specified a device name
# - - - - - - - - - - - - - - - - - - - - - - - -
if [ -z "$DEVICE" ]
then
	echo "Please specify an android device to check, by name, as the first parameter"
	exit -1;
fi;


echo "#################################################"
echo "# Verifying that device $DEVICE is running"
echo "#################################################"

	
MATCHING_DEVICES=`adb devices | awk '{print $1}' | grep $DEVICE | wc -l`

if [ "1" -eq "$MATCHING_DEVICES" ]
then
	echo "Device $DEVICE found"
	exit 0;
else
	echo "ERROR: No device named $DEVICE was found.";
	echo "If the device is really running, you may have to restart your adb server with 'adb kill-server'";
	adb devices;
	exit -1;
fi;