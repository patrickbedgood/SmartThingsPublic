/**
 *  Griddy Manager
 *
 *  Copyright 2018 Trent Foley
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
    name: "Griddy Manager",
    namespace: "trentfoley",
    author: "Trent Foley",
    description: "Provisions a Griddy device handler and facilitates Web API communication with Griddy",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png") {
    appSetting "email"
    appSetting "password"
}

preferences {
    section("Griddy Auth") {
        input "email", "text", title: "Username"
        input "password", "password", title: "Password"
    }
}

def getChildNamespace() { "trentfoley" }
def getChildName() { "Griddy Wholesale Energy Price" }
def getServerUrl() { "https://app.gogriddy.com" }

private debugEvent(message, displayEvent = false) {
    def results = [
        name: "debug",
        descriptionText: message,
        displayed: displayEvent
    ]
    log.debug "${results}"
    sendEvent(results)
}

private errorEvent(message, displayEvent = true) {
    def results = [
        name: "error",
        descriptionText: message,
        displayed: displayEvent
    ]
    log.error "${results}"
    sendEvent(results)
}

def installed() {
	debugEvent("Installed with settings: ${settings}")
	initialize()
}

def updated() {
	debugEvent("Updated with settings: ${settings}")
	unsubscribe()
	initialize()
}

def initialize() {
	def device = addDevice("griddy")
	device.refresh()
	// TODO: subscribe to attributes, devices, locations, etc.
}

private def addDevice(dni) {
    def device = getChildDevice(dni)
    if(!device) {
        device = addChildDevice(childNamespace, childName, dni, null, [ label: "${childName}" ])
        debugEvent("Created ${device.displayName} with device network id: ${dni}")
    } else {
        debugEvent("Found already existing ${device.displayName} with device network id: ${dni}")
    }
    return device
}

// Poll Child is invoked from the Child Device itself as part of the Poll Capability
def pollChild(child) {
    def data = [:]

    data = [
    	price: 9.5
    ]
    
    return data
}