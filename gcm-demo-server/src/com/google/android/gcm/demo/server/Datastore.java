/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gcm.demo.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Simple implementation of a data store using standard Java collections.
 * <p>
 * This class is thread-safe but not persistent (it will lost the data when the
 * app is restarted) - it is meant just as an example.
 */
public final class Datastore {

	@SuppressWarnings("serial")
	private static final List<String> regIds = new ArrayList<String>() {
		{
			// only for debug purposes  - in real world it should be kept in the database
			//nexus emulator
			add("APA91bEt1UBU-qKxnC-aANxiOR-4zU3QJFN9Pcyv4cRQLY_w1x_4IcwgJNhEps7cUZjG8FFpV7eG13OYeqZueVmHh-9DoCpfwvS9gX4hKDugvmQUbIDpl0KL7O3RP5OLVptGwPlUBtrfGAnFdFTLdLERXdCjNErryw");
			//real lenevo
			add("APA91bEDiNzzAbzN3nZCISccaG2pwzBDsof-qmRWYGdLYtD-ZhPVi3wKLrYwPt5ypdzjGp1saL_YGE_Af4LVIH03VF3uEz22C_Ft4txu6xa1qL6uLS21BL0TrI97p5Wexo5CHw-9wWOAJFvETXmCqBaSmwCb791FhQ");
		}
	};
	private static final Logger logger = Logger.getLogger(Datastore.class
			.getName());

	private Datastore() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Registers a device.
	 */
	public static void register(String regId) {
		logger.info("Registering " + regId);
		synchronized (regIds) {
			regIds.add(regId);
		}
	}

	/**
	 * Unregisters a device.
	 */
	public static void unregister(String regId) {
		logger.info("Unregistering " + regId);
		synchronized (regIds) {
			regIds.remove(regId);
		}
	}

	/**
	 * Updates the registration id of a device.
	 */
	public static void updateRegistration(String oldId, String newId) {
		logger.info("Updating " + oldId + " to " + newId);
		synchronized (regIds) {
			regIds.remove(oldId);
			regIds.add(newId);
		}
	}

	/**
	 * Gets all registered devices.
	 */
	public static List<String> getDevices() {
		synchronized (regIds) {
			return new ArrayList<String>(regIds);
		}
	}

}
