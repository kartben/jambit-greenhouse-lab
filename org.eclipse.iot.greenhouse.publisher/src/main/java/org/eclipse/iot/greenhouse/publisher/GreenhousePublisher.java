/**
 * Copyright (c) 2014 Eclipse Foundation
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Benjamin Cabé, Eclipse Foundation
 */
package org.eclipse.iot.greenhouse.publisher;

import java.util.Map;

import org.eclipse.iot.greenhouse.sensors.SensorService;
import org.eclipse.iot.greenhouse.sensors.SensorService.NoSuchSensorOrActuatorException;
import org.eclipse.iot.greenhouse.sensors.SensorChangedListener;
import org.eclipse.kura.KuraException;
import org.eclipse.kura.KuraNotConnectedException;
import org.eclipse.kura.KuraTimeoutException;
import org.eclipse.kura.configuration.ConfigurableComponent;
import org.eclipse.kura.data.DataService;
import org.eclipse.kura.data.DataServiceListener;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreenhousePublisher implements ConfigurableComponent,
		DataServiceListener, SensorChangedListener {
	private static final Logger s_logger = LoggerFactory
			.getLogger(GreenhousePublisher.class);

	private static final String PUBLISH_TOPICPREFIX_PROP_NAME = "publish.appTopicPrefix";
	private static final String PUBLISH_QOS_PROP_NAME = "publish.qos";
	private static final String PUBLISH_RETAIN_PROP_NAME = "publish.retain";

	private DataService _dataService;
	private SensorService _sensorService;

	private Map<String, Object> _properties;

	// ----------------------------------------------------------------
	//
	// Dependencies
	//
	// ----------------------------------------------------------------

	public GreenhousePublisher() {
		super();
	}

	protected void setGreenhouseSensorService(SensorService sensorService) {
		_sensorService = sensorService;
	}

	protected void unsetGreenhouseSensorService(SensorService sensorService) {
		_sensorService = null;
	}

	public void setDataService(DataService dataService) {
		_dataService = dataService;
	}

	public void unsetDataService(DataService dataService) {
		_dataService = null;
	}

	// ----------------------------------------------------------------
	//
	// Activation APIs
	//
	// ----------------------------------------------------------------

	protected void activate(ComponentContext componentContext,
			Map<String, Object> properties) {
		_properties = properties;
		s_logger.info("Activating GreenhousePublisher... Done.");
	}

	protected void deactivate(ComponentContext componentContext) {
		s_logger.debug("Deactivating GreenhousePublisher... Done.");
	}

	public void updated(Map<String, Object> properties) {
		_properties = properties;
	}

	@Override
	public void onConnectionEstablished() {
		String prefix = (String) _properties.get(PUBLISH_TOPICPREFIX_PROP_NAME);

		// TODO! listen to MQTT topic where we expect to receive commands (e.g. toggle LED)
		
	}

	@Override
	public void onDisconnecting() {

	}

	@Override
	public void onDisconnected() {

	}

	@Override
	public void onConnectionLost(Throwable cause) {

	}

	@Override
	public void onMessageArrived(String topic, byte[] payload, int qos,
			boolean retained) {
		String prefix = (String) _properties.get(PUBLISH_TOPICPREFIX_PROP_NAME);

		if (!topic.startsWith(prefix)) {
			return;
		}

		String[] topicFragments = topic.split("/");
		// topicFragments[0] == {appSetting.topic_prefix}
		// topicFragments[1] == {unique_id}
		// topicFragments[2] == "actuators"
		// topicFragments[3] == {actuatorName} (e.g. light)

		if (topicFragments.length != 4)
			return;

		// TODO! control LED using SensorService
	}

	@Override
	public void onMessagePublished(int messageId, String topic) {
	}

	@Override
	public void onMessageConfirmed(int messageId, String topic) {
	}

	@Override
	public void sensorChanged(String sensorName, Object newValue) {
		// Publish the message
		String prefix = (String) _properties.get(PUBLISH_TOPICPREFIX_PROP_NAME);
		Integer qos = (Integer) _properties.get(PUBLISH_QOS_PROP_NAME);
		Boolean retain = (Boolean) _properties.get(PUBLISH_RETAIN_PROP_NAME);

		// TODO!

	}
}
