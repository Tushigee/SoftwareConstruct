/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package pingball.server.tests;

import org.junit.*;

import static org.junit.Assert.*;

import pingball.networking.*;

import javax.json.JsonObject;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static pingball.server.tests.SystemTestUtil.*;

/**
 * @category no_didit
 */
public class SystemTest {

    static ThreadWithObituary serverThread;
    static PipedOutputStream outputStream;
    static PrintWriter writer;

    @BeforeClass
    public static void startUp() throws IOException{
        outputStream = new PipedOutputStream();
        writer = new PrintWriter(outputStream);
        serverThread = startPingballServer(new PipedInputStream(outputStream));
    }

    @Test(timeout = 10000)
    public void sendBRM() throws InterruptedException, IOException{
        Socket connectionSource = connectToPingballServer(serverThread);
        Socket connectionDestination = connectToPingballServer(serverThread);

        BlockingQueue<JsonObject> sInbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> sOutbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> dInbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> dOutbox = new LinkedBlockingQueue<>();

        MessageReceiver sMessageReceiver = new MessageReceiver(connectionSource.getInputStream(), sInbox, System.out);
        MessageDispatcher sMessageDispatcher = new MessageDispatcher(connectionSource.getOutputStream(), sOutbox, System.out);
        MessageReceiver dMessageReceiver = new MessageReceiver(connectionDestination.getInputStream(), dInbox, System.out);
        MessageDispatcher dMessageDispatcher = new MessageDispatcher(connectionDestination.getOutputStream(), dOutbox, System.out);

        Thread sRecvThread = new Thread(sMessageReceiver);
        sRecvThread.start();
        Thread dRecvThread = new Thread(dMessageReceiver);
        dRecvThread.start();
        Thread sSendThread = new Thread(sMessageDispatcher);
        sSendThread.start();
        Thread dSendThread = new Thread(dMessageDispatcher);
        dSendThread.start();

        GameScenario scenario = GameScenario.generateRandomWallToWall();
        sOutbox.put(scenario.generateCIMSource());
        dOutbox.put(scenario.generateCIMDestination());
        Thread.sleep(100);
        writer.println(scenario.generateWallConnection());
        writer.flush();
        Thread.sleep(100);
        sOutbox.put(scenario.generateBRMWithoutDestination());

        JsonObject wcim = dInbox.take();
        JsonObject brmMessage = dInbox.take();
        BallRoutingMessage brm = MessageProcessor.extractBallRoutingMessage(brmMessage);
        assertEquals(brm.getBall().getPosition(), scenario.getBall().getPosition());
        assertEquals(brm.getBall().getVelocity(), scenario.getBall().getVelocity());
        assertEquals(brm.getSource(), scenario.getSource());
        assertEquals(brm.getDestination(), scenario.getDestination());

        connectionDestination.close();
        connectionSource.close();
    }

    @Test(timeout = 10000)
    public void sendBRMDestinationUpdate() throws InterruptedException, IOException{
        Socket connectionSource = connectToPingballServer(serverThread);
        Socket connectionDestination = connectToPingballServer(serverThread);

        BlockingQueue<JsonObject> sInbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> sOutbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> dInbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> dOutbox = new LinkedBlockingQueue<>();

        MessageReceiver sMessageReceiver = new MessageReceiver(connectionSource.getInputStream(), sInbox, System.out);
        MessageDispatcher sMessageDispatcher = new MessageDispatcher(connectionSource.getOutputStream(), sOutbox, System.out);
        MessageReceiver dMessageReceiver = new MessageReceiver(connectionDestination.getInputStream(), dInbox, System.out);
        MessageDispatcher dMessageDispatcher = new MessageDispatcher(connectionDestination.getOutputStream(), dOutbox, System.out);

        Thread sRecvThread = new Thread(sMessageReceiver);
        sRecvThread.start();
        Thread dRecvThread = new Thread(dMessageReceiver);
        dRecvThread.start();
        Thread sSendThread = new Thread(sMessageDispatcher);
        sSendThread.start();
        Thread dSendThread = new Thread(dMessageDispatcher);
        dSendThread.start();

        GameScenario scenario = GameScenario.generateRandomWallToWall();
        sOutbox.put(scenario.generateCIMSource());
        dOutbox.put(scenario.generateCIMDestination());
        Thread.sleep(100);
        writer.println(scenario.generateWallConnection());
        writer.flush();

        scenario = GameScenario.generateRandomWallToWall();
        writer.println(scenario.generateWallConnection());
        writer.flush();
        Thread.sleep(100);

        sOutbox.put(scenario.generateBRMWithoutDestination());
        JsonObject wcim = dInbox.take();
        JsonObject wcim2 = dInbox.take();
        JsonObject wcim3 = dInbox.take();
        JsonObject wcim4 = dInbox.take();
        JsonObject brmMessage = dInbox.take();
        BallRoutingMessage brm = MessageProcessor.extractBallRoutingMessage(brmMessage);
        assertEquals(brm.getBall().getPosition(), scenario.getBall().getPosition());
        assertEquals(brm.getBall().getVelocity(), scenario.getBall().getVelocity());
        assertEquals(brm.getSource(), scenario.getSource());
        assertEquals(brm.getDestination(), scenario.getDestination());

        connectionDestination.close();
        connectionSource.close();
    }

    @Test(timeout = 10000)
    public void bounceBackBRMnoConnection() throws InterruptedException, IOException{
        Socket connectionSource = connectToPingballServer(serverThread);
        Socket connectionDestination = connectToPingballServer(serverThread);

        BlockingQueue<JsonObject> sInbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> sOutbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> dInbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> dOutbox = new LinkedBlockingQueue<>();

        MessageReceiver sMessageReceiver = new MessageReceiver(connectionSource.getInputStream(), sInbox, System.out);
        MessageDispatcher sMessageDispatcher = new MessageDispatcher(connectionSource.getOutputStream(), sOutbox, System.out);
        MessageReceiver dMessageReceiver = new MessageReceiver(connectionDestination.getInputStream(), dInbox, System.out);
        MessageDispatcher dMessageDispatcher = new MessageDispatcher(connectionDestination.getOutputStream(), dOutbox, System.out);

        Thread sRecvThread = new Thread(sMessageReceiver);
        sRecvThread.start();
        Thread dRecvThread = new Thread(dMessageReceiver);
        dRecvThread.start();
        Thread sSendThread = new Thread(sMessageDispatcher);
        sSendThread.start();
        Thread dSendThread = new Thread(dMessageDispatcher);
        dSendThread.start();

        GameScenario scenario = GameScenario.generateRandomWallToWall();
        sOutbox.put(scenario.generateCIMSource());
        sOutbox.put(scenario.generateBRMWithDestination());

        JsonObject brmMessage = sInbox.take();
        BallRoutingMessage brm = MessageProcessor.extractBallRoutingMessage(brmMessage);
        assertEquals(brm.getBall().getPosition(), scenario.getBall().getPosition());
        assertEquals(brm.getBall().getVelocity(), scenario.getBall().getVelocity());
        assertEquals(brm.getSource(), scenario.getSource());
        assertEquals(brm.getDestination(), scenario.getSource());

        connectionDestination.close();
        connectionSource.close();
    }

    /*@Test(timeout = 10000)
    public void bounceBackBRMdeletedConnection() throws InterruptedException, IOException{
        Socket connectionSource = connectToPingballServer(serverThread);
        Socket connectionDestination = connectToPingballServer(serverThread);

        BlockingQueue<JsonObject> sInbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> sOutbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> dInbox = new LinkedBlockingQueue<>();
        BlockingQueue<JsonObject> dOutbox = new LinkedBlockingQueue<>();

        MessageReceiver sMessageReceiver = new MessageReceiver(connectionSource.getInputStream(), sInbox, System.out);
        MessageDispatcher sMessageDispatcher = new MessageDispatcher(connectionSource.getOutputStream(), sOutbox, System.out);
        MessageReceiver dMessageReceiver = new MessageReceiver(connectionDestination.getInputStream(), dInbox, System.out);
        MessageDispatcher dMessageDispatcher = new MessageDispatcher(connectionDestination.getOutputStream(), dOutbox, System.out);

        Thread sRecvThread = new Thread(sMessageReceiver);
        sRecvThread.start();
        Thread dRecvThread = new Thread(dMessageReceiver);
        dRecvThread.start();
        Thread sSendThread = new Thread(sMessageDispatcher);
        sSendThread.start();
        Thread dSendThread = new Thread(dMessageDispatcher);
        dSendThread.start();

        GameScenario scenario = GameScenario.generateRandomWallToWall();
        sOutbox.put(scenario.generateCIMSource());
        dOutbox.put(scenario.generateCIMDestination());
        Thread.sleep(100);
        writer.println(scenario.generateWallConnection());
        writer.flush();
        Thread.sleep(100);
        connectionDestination.close();
        sOutbox.put(scenario.generateBRMWithoutDestination());

        JsonObject wcim = sInbox.take();
        JsonObject wcim2 = sInbox.take();
        JsonObject brmMessage = sInbox.take();
        BallRoutingMessage brm = MessageProcessor.extractBallRoutingMessage(brmMessage);
        assertEquals(brm.getBall().getPosition(), scenario.getBall().getPosition());
        assertEquals(brm.getBall().getVelocity(), scenario.getBall().getVelocity());
        assertEquals(brm.getSource(), scenario.getSource());
        assertEquals(brm.getDestination(), scenario.getSource());

        connectionDestination.close();
        connectionSource.close();
    }*/

    /*@After
    public void tearDown() throws Exception {
        System.out.println("\n\ndone\n\n");
        if (connectionDestination.isConnected()) connectionDestination.close();
        connectionSource.close();
    }*/

}
