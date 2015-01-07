package pingball.server.tests;

import static org.junit.Assert.assertTrue;
import static pingball.server.tests.SystemTestUtil.startPingballServer;

import org.junit.Test;

import pingball.server.PingballServer;
import pingball.server.ServerConnection;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Partitioning test space into following partitions:
 * 1. Test whether the object is running successfully
 * 
 */
// We are not required to test more than that since ServerConnection is just 
// using MessageReceiver and MessageDispatcher objects to handle input and output connection
// 
public class ServerConnectionTest {

    /**
     * Tests startup of server connection handler
     * 
     * @category no_didit
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    (timeout=1000)
    public void testSuccessfulStart() throws IOException, InterruptedException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        startPingballServer(inputStream, "--port", String.valueOf(PingballServer.DEFAULT_PORT));
        
        Thread.sleep(10);
        
        Socket socket = new Socket("127.0.0.1", PingballServer.DEFAULT_PORT);
        ServerConnection serverConnection = new ServerConnection(socket);
        serverConnection.startReceiver();
        serverConnection.startDispatcher();
        // indicating that code is initialized successful. 
        assertTrue(true);
        
        socket.close();
        writer.close();
        inputStream.close();
    }
}
