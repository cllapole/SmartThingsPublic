/**
 *  Garage Door open/close
 *
 *  Copyright 2017 Chris LaPole
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Garage Door open/close",
    namespace: "cllapole",
    author: "Chris LaPole",
    description: "Ability to open/close garage door, correct trigger based on current status; and notify if left open when no presence devices detected.",
    category: "Safety & Security",
    iconUrl: "http://www.geniecompany.com/images/products/accessories/remote_33069R_GIT-1.jpg",
    iconX2Url: "http://www.geniecompany.com/images/products/accessories/remote_33069R_GIT-1.jpg",
    iconX3Url: "http://www.geniecompany.com/images/products/accessories/remote_33069R_GIT-1.jpg")
    
/*  Full plan - A button press toggles power to an outlet for 3 seconds that controls a relay that will signal the garage door to open/close.
 *  Needs to have both an open mode and close mode that determines the state of the garage door via a door/window sensor.
 *  Notification (text message) of whether it's state was already as desired or if it completed and the door/window sensor changed status.
 *  Notification if presence devices (both smartphones) show away and the contact shows open; maybe add a 3 minute delay first.
 *  Include in the Armed/Away routines.
 *  
 *  
*/

preferences {
	section("Select the devices") {
		input "button_open1", "capability.button", required: true, multiple: false, title: "Select an open button", displayDuringSetup: true
        input "button_close1", "capability.button", required: true, multiple: false, title: "Select a closed button", displayDuringSetup: true
        input "sensor1", "capability.contactSensor", required: true, multiple: false, title: "Select a contact sensor", displayDuringSetup: true
        input "outlet1", "capability.switch", required: true, multiple: false, title: "Select an outlet", displayDuringSetup: true
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribe(button_open1, "button.pushed", openHandler)
    subscribe(button_close1, "button.pushed", closeHandler)
}

def updated(settings) {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	subscribe(button_open1, "button.pushed", openHandler)
    subscribe(button_close1, "button.pushed", closeHandler)
}

def initialize() {
    outlet1.off()
}

def openHandler(evt) {
	log.trace "Wait, opening garage door..."
	outlet1.on()
    outlet1.off([delay:3000])
}

def closeHandler(evt) {
	log.trace "Wait, closing garage door..."
	outlet1.on() 
    outlet1.off([delay:3000])
}

// TODO: implement event handlers

