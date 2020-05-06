/*
 * Copyright (c) Kay Fichtelmann 2020.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.fichtelmannsoftware.manualgherkintests.parser

import io.cucumber.java8.En
import io.cucumber.java8.PendingException

class SupportTags : En {
    init {
        Given("One feature file have the tag {string}") { tagName: String? ->
            throw PendingException()
        }

        When("the user starts the Parser with the directory of feature files: {string}") { path: String? ->
            throw PendingException()
        }

        Then("only the feature {string} should be parsed") { featureName: String? ->
            throw PendingException()
        }
    }
}