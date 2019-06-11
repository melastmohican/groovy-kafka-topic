#!/usr/bin/env groovy

@Grab(group = "org.apache.kafka", module = "kafka-clients", version = "2.2.0")
@Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.2.3')


import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

Properties properties = new Properties()
new File('./settings.local.properties').withInputStream {
    properties.load(it)
}

println properties

AdminClient adminClient = AdminClient.create(properties);

println "Topics:"
adminClient.listTopics().names().get().each {
    println it
}

NewTopic newTopic = new NewTopic("test", 50, (short)1); //new NewTopic(topicName, numPartitions, replicationFactor)

List<NewTopic> newTopics = new ArrayList<NewTopic>();
newTopics.add(newTopic);

adminClient.createTopics(newTopics);

println "Topics:"
adminClient.listTopics().names().get().each {
    println it
}

adminClient.close();

