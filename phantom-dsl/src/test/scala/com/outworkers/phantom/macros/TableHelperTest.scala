/*
 * Copyright 2013 - 2017 Outworkers Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.outworkers.phantom.macros

import com.outworkers.phantom.PhantomSuite
import org.joda.time.DateTime
import com.outworkers.phantom.dsl._

class TableHelperTest extends PhantomSuite {

  it should "not generate a fromRow if a normal type is different" in {

    case class SampleEvent(id: String, map: Map[Long, DateTime])

    class Events extends CassandraTable[Events, SampleEvent] {
      object id extends UUIDColumn(this) with PartitionKey
      object map extends MapColumn[Long, Long](this)
    }

    val ev = new Events()
    intercept[NotImplementedError] {
      ev.fromRow(null.asInstanceOf[Row])
    }
  }

  it should "not generate a fromRow if the columns match but are in the wrong order" in {

    case class Event(
      id: UUID,
      text: String,
      length: Int,
      map: Map[Long, DateTime]
    )

    class Events extends CassandraTable[Events, Event] {
      object id extends UUIDColumn(this) with PartitionKey
      object text extends StringColumn(this)
      object map extends MapColumn[Long, Long](this)
      object length extends IntColumn(this)
    }

    val ev = new Events()
    intercept[NotImplementedError] {
      ev.fromRow(null.asInstanceOf[Row])
    }
  }

  it should "not generate a fromRow if the argument passed to a map column is different" in {

    case class SampleEvent(id: UUID, map: Map[Long, DateTime])

    class Events extends CassandraTable[Events, SampleEvent] {
      object id extends UUIDColumn(this) with PartitionKey
      object map extends MapColumn[Long, Long](this)
    }

    val ev = new Events()
    intercept[NotImplementedError] {
      ev.fromRow(null.asInstanceOf[Row])
    }
  }

  it should "not generate a fromRow if  the collection type is different" in {
    case class Ev(id: UUID, set: List[Int])

    class Events extends CassandraTable[Events, Ev] {
      object id extends UUIDColumn(this) with PartitionKey
      object map extends SetColumn[Int](this)
    }

    val ev = new Events()
    intercept[NotImplementedError] {
      ev.fromRow(null.asInstanceOf[Row])
    }
  }

  it should "not generate a fromRow if the argument passed to a list column is different" in {
    case class Ev(id: UUID, set: List[Int])

    class Events extends CassandraTable[Events, Ev] {
      object id extends UUIDColumn(this) with PartitionKey
      object map extends ListColumn[String](this)
    }

    val ev = new Events()
    intercept[NotImplementedError] {
      ev.fromRow(null.asInstanceOf[Row])
    }
  }

  it should "not generate a fromRow if the argument passed to a set column is different" in {
    case class Ev(id: UUID, set: Set[Int])

    class Events extends CassandraTable[Events, Ev] {
      object id extends UUIDColumn(this) with PartitionKey
      object map extends SetColumn[String](this)
    }

    val ev = new Events()
    intercept[NotImplementedError] {
      ev.fromRow(null.asInstanceOf[Row])
    }
  }
}