/*
 *  RapidMiner
 *
 *  Copyright (C) 2001-2014 by RapidMiner and the contributors
 *
 *  Complete list of developers available at our web site:
 *
 *       http://rapidminer.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.operator.features.construction;

import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.features.AbstractFeatureProcessing;
import com.rapidminer.operator.ports.metadata.ExampleSetMetaData;
import com.rapidminer.operator.ports.metadata.MetaData;
import com.rapidminer.parameter.UndefinedParameterError;

/**
 * Abstract superclass of all feature processing operators who generate new features.
 * 
 * @author Simon Fischer
 */
public abstract class AbstractFeatureConstruction extends AbstractFeatureProcessing {

	public AbstractFeatureConstruction(OperatorDescription description) {
		super(description);
	}

	@Override
	protected MetaData modifyMetaData(ExampleSetMetaData metaData) throws UndefinedParameterError {
		metaData.attributesAreSuperset();
		return metaData;
	}

	/**
	 * These operators will build additional columns, but won't touch existing ones.
	 */
	@Override
	public boolean writesIntoExistingData() {
		return false;
	}

}
