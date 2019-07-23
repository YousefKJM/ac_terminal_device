package daccess;
//START SNIPPET: control-gpio-snippet

/*
* #%L
* **********************************************************************
* ORGANIZATION  :  Pi4J
* PROJECT       :  Pi4J :: Java Examples
* FILENAME      :  ControlGpioExample.java
*
* This file is part of the Pi4J project. More information about
* this project can be found here:  https://www.pi4j.com/
* **********************************************************************
* %%
* Copyright (C) 2012 - 2019 Pi4J
* %%
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation, either version 3 of the
* License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Lesser Public License for more details.
*
* You should have received a copy of the GNU General Lesser Public
* License along with this program.  If not, see
* <http://www.gnu.org/licenses/lgpl-3.0.html>.
* #L%
*/

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
* This example code demonstrates how to perform simple state
* control of a GPIO pin on the Raspberry Pi.
*
* @author Robert Savage
*/


public class GPIO implements GpioPinListenerDigital {
	Manager mgr; 
	GpioController gpio;
	GpioPinDigitalOutput magnetPin;
	 public GPIO(Manager mg) {
	     mgr= mg;
		 System.out.println("GPIO Controller started");
	     // create gpio controller
	     gpio = GpioFactory.getInstance();
	     // provision gpio pin #01 as an output pin and turn on
	     magnetPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Magnet", PinState.HIGH);
	     // set shutdown state for this pin
	     magnetPin.setShutdownOptions(true, PinState.HIGH);
	     
	     GpioPinDigitalInput doorStatus = gpio.provisionDigitalInputPin(RaspiPin.GPIO_08,             // PIN NUMBER
                 "Status",                   // PIN FRIENDLY NAME (optional)
                 PinPullResistance.PULL_UP); // PIN RESISTANCE (optional)
	     doorStatus.addListener(this);    

	 }
	 
	 public void openDoor()
	 {
		 magnetPin.high();
	 }
	 
	 public void closeDoor()
	 {
		 magnetPin.low();
	 }

	@Override
	public void handleGpioPinDigitalStateChangeEvent(
			GpioPinDigitalStateChangeEvent arg0) {
		// TODO Auto-generated method stub
        // display pin state on console
		if (arg0.getState().isHigh())
			mgr.confirmSeamless();
	}
}
