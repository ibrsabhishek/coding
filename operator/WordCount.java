package com.ibm.security.jgss.test;
import org.ietf.jgss.*;
import com.ibm.security.jgss.Debug;
 
import java.io.*;
import java.net.*;
import java.util.*;
 
/**
 * A JGSS sample client; 
 * to be used in conjunction with the JGSS sample server.
 * The client first establishes a context with the server
 * and then sends wrapped message followed by a MIC to the server.
 * The MIC is calculated over the plain text that was wrapped.
 * The client requires to server to authenticate itself 
 * (mutual authentication) during context establishment.
 * It also delegates its credentials to the server.
 * 
 * It sets the JAVA variable 
 * javax.security.auth.useSubjectCredsOnly to false
 * so that JGSS will not acquire credentials through JAAS.
 * 
 * The client takes input parameters, and complements it 
 * with information from the jgss.ini file; any required input not
 * supplied on the command line is taking from the jgss.ini file.
 * 
 * Usage: Client [options]
 * 
 * The -? option produces a help message including supported options.
 * 
 * This sample client does not use JAAS.
 * The client can be run against the JAAS sample client and server.
 * See {@link JAASClient JAASClient} for a sample client that uses JAAS.
 */
 
class Client 
{
    private Util testUtil      = null;
    private String myName      = null;
    private GSSName gssName    = null;
    private String serverName = null;
    private int servicePort    = 0;
    private GSSManager mgr     = GSSManager.getInstance();
    private GSSName service    = null;
    private GSSContext context        = null;
    private String program     = "Client";
    private String debugPrefix = "Client: ";
    private TCPComms tcp       = null;
    private String data               = null;
    private byte[] dataBytes   = null;
    private String serviceHostname= null;
    private GSSCredential gssCred = null;
 
    private static Debug debug        = new Debug();
 
    private static final String usageString =
          "\t[-?] [-d | -n name] [-s serverName]"
        + "\n\t[-h serverHost [:port]] [-p port] [-m msg]" 
        + "\n"
        + "\n  -?\t\t\thelp; produces this message"
        + "\n  -n name\t\tthe client's principal name (without realm)"
        + "\n  -s serverName\t\tthe server's principal name (without realm)"
        + "\n  -h serverHost[:port]\tthe server's hostname"
        +         " (and optional port number)"
        + "\n  -p port\t\tthe port on which the server will be listening"
        + "\n  -m msg\t\tmessage to send to the server";
 
 
    // Caller must call initialize (may need to call processArgs first).
    public Client (String programName) throws Exception
    {
        testUtil = new Util();
        if (programName != null)
        {
            program = programName;
            debugPrefix = programName + ": ";
        }
    }
 
    // Caller must call initialize (may need to call processArgs first).
    Client (String programName, boolean useSubjectCredsOnly) throws Exception
    {
        this(programName);
        setUseSubjectCredsOnly(useSubjectCredsOnly);
    }
 
    public Client(GSSCredential myCred,
                  String serverNameWithoutRealm,
                  String serverHostname,
                  int serverPort,
                  String message)
        throws Exception
    {
        testUtil = new Util();
 
        if (myCred != null)
        {
            gssCred = myCred;
        }
        else
        {
            throw new GSSException(GSSException.NO_CRED, 0,
                                       "Null input credential");
        }
 
        init(serverNameWithoutRealm, serverHostname, serverPort, message);
    }
 
    void setUseSubjectCredsOnly(boolean useSubjectCredsOnly)
    {
        final String subjectOnly = useSubjectCredsOnly ? "true" : "false";
        final String property = "javax.security.auth.useSubjectCredsOnly";
 
        String temp = (String)java.security.AccessController.doPrivileged(
                        new sun.security.action.GetPropertyAction(property));
 
        if (temp == null)
        {
            debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
              + "setting useSubjectCredsOnly property to "
              + useSubjectCredsOnly);
 
            // Property not set. Set it to the specified value.
 
            java.security.AccessController.doPrivileged(
                 new java.security.PrivilegedAction() {
                   public Object run() {
                      System.setProperty(property, subjectOnly);
                      return null;
                   }
                 });
        }
        else
        {
            debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
              + "useSubjectCredsOnly property already set "
              + "in JVM  to " + temp);
        }
    }
 
    private void init(String myNameWithoutRealm,
                      String serverNameWithoutRealm,
                      String serverHostname,
                      int    serverPort,
                      String message) throws Exception
    {
        myName = myNameWithoutRealm;
        init(serverNameWithoutRealm, serverHostname, serverPort, message);
    }
 
    private void init(String serverNameWithoutRealm,
                      String serverHostname,
                      int    serverPort,
                      String message) throws Exception
    {
        // peer's name
        if (serverNameWithoutRealm != null)
        {
            this.serverName = serverNameWithoutRealm;
        }
        else
        {
            this.serverName = testUtil.getDefaultServicePrincipalWithoutRealm();
        }
 
        // peer's host
        if (serverHostname != null)
        {
            this.serviceHostname = serverHostname;
        }
        else
        {
            this.serviceHostname = testUtil.getDefaultServiceHostname();
        }
 
        // peer's port
        if (serverPort > 0)
        {
            this.servicePort = serverPort;
        }
        else
        {
            this.servicePort = testUtil.getDefaultServicePort();
        }
 
        // message for peer
        if (message != null)
        {
            this.data = message;
        }
        else
        {
            this.data = "The quick brown fox jumps over the lazy dog";
        }
 
        this.dataBytes = this.data.getBytes();
 
        tcp = new TCPComms(serviceHostname, servicePort);
    }
                   
 
    void initialize() throws Exception
    {
         Oid krb5MechanismOid = new Oid("1.2.840.113554.1.2.2");
 
        if (gssCred == null)
        {
            if (myName != null)
            {
                debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                                   + "creating GSSName USER_NAME for " 
                                   + myName);
 
                gssName = mgr.createName(
                               myName,
                               GSSName.NT_USER_NAME,
                                krb5MechanismOid);
  
                debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                                   + "Canonicalized GSSName=" + gssName);
            }
            else
                gssName = null; // for default credentials
 
  
            debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix + "creating"
               + ((gssName == null)? " default " : " ") 
               + "credential");
 
            gssCred = mgr.createCredential(
                                    gssName,
                                    GSSCredential.DEFAULT_LIFETIME,
                                    (Oid)null,
                                    GSSCredential.INITIATE_ONLY);
            if (gssName == null)
            {
                gssName = gssCred.getName();
 
                myName  = gssName.toString();
 
                debug.out(Debug.OPTS_CAT_APPLICATION, 
                    debugPrefix + "default credential principal=" + myName);
            }
        }
 
        debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix + gssCred);
   
        debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix
           + "creating canonicalized GSSName for serverName " + serverName);
 
        service = mgr.createName(serverName, 
                                 GSSName.NT_HOSTBASED_SERVICE,
                                  krb5MechanismOid);
 
        debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
           + "Canonicalized server name = " + service);
 
        debug.out(Debug.OPTS_CAT_APPLICATION, 
                            debugPrefix + "Raw data=" + data);
    }
 
 
    void establishContext(BitSet flags) throws Exception
    {
        try {
 
          debug.out(Debug.OPTS_CAT_APPLICATION, 
                            debugPrefix + "creating GSScontext");
 
          Oid defaultMech = null;
          context = mgr.createContext(service, defaultMech, gssCred, 
                                      GSSContext.INDEFINITE_LIFETIME);
 
          if (flags != null)
          {
              if (flags.get(Util.CONTEXT_OPTS_MUTUAL))
              {
                  debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                       + "requesting mutualAuthn");
 
                  context.requestMutualAuth(true);
              }
 
              if (flags.get(Util.CONTEXT_OPTS_INTEG))
              {
                  debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                       + "requesting integrity");
 
                  context.requestInteg(true);
              }
 
              if (flags.get(Util.CONTEXT_OPTS_CONF))
              {
                  context.requestConf(true);
                  debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                          + "requesting confidentiality");
              }
 
              if (flags.get(Util.CONTEXT_OPTS_DELEG))
              {
                  context.requestCredDeleg(true);
                  debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                           + "requesting delegation");
              }
 
              if (flags.get(Util.CONTEXT_OPTS_REPLAY))
              {
                  context.requestReplayDet(true);
                  debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                      + "requesting replay detection");
              }
 
              if (flags.get(Util.CONTEXT_OPTS_SEQ))
              {
                  context.requestSequenceDet(true);
                  debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                           + "requesting out-of-sequence detection");
              }
              // Add more later!
          }
 
          byte[] response = null;
          byte[] request = null;
          int len = 0;
          boolean done = false;
          do {
              debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                  + "Calling initSecContext");
 
              request = context.initSecContext(response, 0, len);
 
              if (request != null)
              {
                  debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix 
                    + "Sending initial context token");
 
                  tcp.send(request);
              }
              done = context.isEstablished();
 
              if (!done)
              {
                  debug.out(Debug.OPTS_CAT_APPLICATION, 
                      debugPrefix + "Receiving response token");
 
                  byte[] temp = tcp.receive();
                  response = temp;
                  len = response.length;
              }
          } while(!done);
 
          debug.out(Debug.OPTS_CAT_APPLICATION, 
                debugPrefix + "context established with acceptor");
          
        } catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
        }
    }
 
    void doMIC() throws Exception
    {
        debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix + "generating MIC");
        byte[] mic = context.getMIC(dataBytes, 0, dataBytes.length, null);
 
        if (mic != null)
        {
            debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix + "sending MIC");
            tcp.send(mic);
        }
        else
            debug.out(Debug.OPTS_CAT_APPLICATION, 
                             debugPrefix + "getMIC Failed");
    }
 
    void doWrap() throws Exception
    {
        MessageProp mp = new MessageProp(true);
        mp.setPrivacy(context.getConfState());
 
        debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix + "wrapping message");
 
        byte[] wrapped = context.wrap(dataBytes, 0, dataBytes.length, mp);
 
        if (wrapped != null)
        {
            debug.out(Debug.OPTS_CAT_APPLICATION,
                    debugPrefix + "sending wrapped message");
 
            tcp.send(wrapped);
        }
        else
            debug.out(Debug.OPTS_CAT_APPLICATION, debugPrefix + "wrap Failed");
    }
 
    void printUsage()
    {
        System.out.println(program + usageString);
    }
 
    void processArgs(String[] args) throws Exception
    {
        String port            = null;
        String myName          = null;
        int servicePort               = 0;
        String serviceHostname = null;
 
        String sHost = null;
        String msg = null;
 
        GetOptions options = new GetOptions(args, "?h:p:m:n:s:");
        int ch = -1;
        while ((ch = options.getopt()) != options.optEOF)
        {
            switch(ch)
            {
                case '?':
                    printUsage();
                    System.exit(1);
 
                case 'h':
                    if (sHost == null)
                    {
                        sHost = options.optArgGet();
                        int p = sHost.indexOf(':');
                        if (p != -1)
                        {
                            String temp1 = sHost.substring(0, p);
                            if (port == null)
                                port = sHost.substring(p+1, sHost.length()).trim();
                            sHost = temp1;
                        }
                    }
                    continue;
 
                case 'p':
                    if (port == null)
                        port = options.optArgGet();
                    continue;
 
                case 'm':
                    if (msg == null)
                        msg = options.optArgGet();
                    continue;
 
                case 'n':
                    if (myName == null)
                        myName = options.optArgGet();
                    continue;
 
                case 's':
                    if (serverName == null)
                        serverName = options.optArgGet();
                    continue;
            }
        }
 
        if ((port != null) && (port.length() > 0))
        {
            int p = -1;
            try {
                p = Integer.parseInt(port);
            } catch (Exception exc) {
                System.out.println("Bad port input: "+port);
            }
 
            if (p != -1)
                servicePort = p;
        }
       
        if ((sHost != null) && (sHost.length() > 0)) {
                serviceHostname = sHost;                
        }
 
        init(myName, serverName, serviceHostname, servicePort, msg);
    }
 
    void interactWithAcceptor(BitSet flags) throws Exception
    {
        establishContext(flags);
        doWrap();
        doMIC();
    }
 
    void interactWithAcceptor() throws Exception
    {
        BitSet flags = new BitSet();
        flags.set(Util.CONTEXT_OPTS_MUTUAL);
        flags.set(Util.CONTEXT_OPTS_CONF);
        flags.set(Util.CONTEXT_OPTS_INTEG);
        flags.set(Util.CONTEXT_OPTS_DELEG);
        interactWithAcceptor(flags);
    }
 
    void dispose() throws Exception
    {
        if (tcp != null)
        {
            tcp.close();
        }
    }
   
    public static void main(String args[]) throws Exception
    {
        System.out.println(debug.toString()); // XXXXXXX
        String programName = "Client";
        Client client = null;
        try {
            client = new Client(programName,
                                false); // don't use Subject creds.
            client.processArgs(args);
            client.initialize();
            client.interactWithAcceptor();
        } catch (Exception exc) {
            debug.out(Debug.OPTS_CAT_APPLICATION, 
                            programName + " Exception: " + exc.toString());
            exc.printStackTrace();
            throw exc;
        } finally {
            try {
                if (client != null)
                    client.dispose();
            } catch (Exception exc) {}
        }
 
        debug.out(Debug.OPTS_CAT_APPLICATION, programName + ": done");
    }
}