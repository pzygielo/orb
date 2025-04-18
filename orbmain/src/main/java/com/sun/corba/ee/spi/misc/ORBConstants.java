/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License v2.0
 * w/Classpath exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause OR GPL-2.0 WITH
 * Classpath-exception-2.0
 */

package com.sun.corba.ee.spi.misc;

import com.sun.corba.ee.org.omg.CORBA.SUNVMCID ;

public class ORBConstants {
    private ORBConstants() {}

    public static final String STRINGIFY_PREFIX = "IOR:" ;

    /* TAGS
       tag-request@omg.org
       FAQ on tags and tag allocation: http://doc.omg.org/ptc/99-02-01.
       http://doc.omg.org/standard-tags
       http://doc.omg.org/vendor-tags

       Last update:  19th August 2003 (ptc/03-08-14)

       // Legacy
       1 profile tag      0x4f4e4300              ("ONC\x00")
       1 profile tag      0x4e454f00              ("NEO\x00")
       1 profile tag      0x434f4f4c              ("COOL")
       16 service tags    0x4e454f00 - 0x4e454f0f ("NEO\x00" - "NEO\x0f")

       // Current
       16 VMCID           0x5355xxxx              ("SU\x00\x00" - "SU\xff\xff")
       16 profile tags    0x53554e00 - 0x53554e0f ("SUN\x00" - "SUN\x0f")
       16 ORB Type IDs    0x53554e00 - 0x53554e0f ("SUN\x00" - "SUN\x0f")
       64 service tags    0x53554e00 - 0x53554e3f ("SUN\x00" - "SUN\x3f")
       64 component tags  0x53554e00 - 0x53554e3f ("SUN\x00" - "SUN\x3f")
    */

    // All NEO service contexts must be in the range 
    // NEO_FIRST_SERVICE_CONTEXT to
    // NEO_FIRST_SERVICE_CONTEXT + NUM_NEO_SERVICE_CONTEXTS - 1
    public static final int NEO_FIRST_SERVICE_CONTEXT = 0x4e454f00 ;
    public static final int NUM_NEO_SERVICE_CONTEXTS = 15 ;
    public static final int TAG_ORB_VERSION = NEO_FIRST_SERVICE_CONTEXT ;

    public static final int SUN_TAGGED_COMPONENT_ID_BASE = 0x53554e00;
    public static final int SUN_SERVICE_CONTEXT_ID_BASE  = 0x53554e00;

    ////////////////////////////////////////////////////
    //
    // Tagged Components Ids
    //

    // Used by AS 7 for IIOP failover.
    public static final int TAG_CONTAINER_ID =
        SUN_TAGGED_COMPONENT_ID_BASE + 0;
    // Used by AS 8.1 for Request Partioning
    public static final int TAG_REQUEST_PARTITIONING_ID = 
        SUN_TAGGED_COMPONENT_ID_BASE + 1;
    // TaggedComponentId for Java serialization tagged component.
    public static final int TAG_JAVA_SERIALIZATION_ID =
        SUN_TAGGED_COMPONENT_ID_BASE + 2;

    // Introduced in AS 9.0
    public static final int FOLB_MEMBER_ADDRESSES_TAGGED_COMPONENT_ID = 
        SUN_TAGGED_COMPONENT_ID_BASE + 3;
    // Introduced in AS 9.0
    public static final int FOLB_MEMBERSHIP_LABEL_TAGGED_COMPONENT_ID =
        SUN_TAGGED_COMPONENT_ID_BASE + 4;
    // Introduced in GFv2.1
    public static final int TAG_LOAD_BALANCING_ID = 
        SUN_TAGGED_COMPONENT_ID_BASE + 5;

    ////////////////////////////////////////////////////
    //
    // Service Context Ids
    //

    // Used by AS 7 for IIOP failover.
    public static final int CONTAINER_ID_SERVICE_CONTEXT =
        SUN_SERVICE_CONTEXT_ID_BASE + 0;        

    // Introduced in AS 9.
    public static final int FOLB_MEMBERSHIP_LABEL_SERVICE_CONTEXT_ID =
        SUN_SERVICE_CONTEXT_ID_BASE + 1;
    // Introduced in AS 9.
    public static final int FOLB_IOR_UPDATE_SERVICE_CONTEXT_ID =
        SUN_SERVICE_CONTEXT_ID_BASE + 2;


    ////////////////////////////////////////////////////
    //
    // All Sun policies are allocated using the SUNVMCID, which is also
    // used for minor codes.  This allows 12 bits of offset, so
    // the largest legal Sun policy is SUNVMCID.value + 4095.
    public static final int SERVANT_CACHING_POLICY      = SUNVMCID.value + 0 ;
    public static final int ZERO_PORT_POLICY            = SUNVMCID.value + 1 ;
    public static final int COPY_OBJECT_POLICY          = SUNVMCID.value + 2 ;
    public static final int REQUEST_PARTITIONING_POLICY = SUNVMCID.value + 3 ;
    public static final int REFERENCE_MANAGER_POLICY    = SUNVMCID.value + 4 ;
    public static final int LOAD_BALANCING_POLICY       = SUNVMCID.value + 5 ;

    // These are the subcontract IDs for various qualities of
    // service/implementation.
    // Persistent SCIDs have the second bit as 1.
    // SCIDs less than FIRST_POA_SCID are JavaIDL SCIDs.
    public static final int TOA_SCID = 2 ;

    public static final int DEFAULT_SCID = TOA_SCID ;

    public static final int FIRST_POA_SCID = 32;
    public static final int MAX_POA_SCID = 63;
    public static final int TRANSIENT_SCID          = FIRST_POA_SCID ;
    public static final int PERSISTENT_SCID         = makePersistent( TRANSIENT_SCID ) ;
    public static final int SC_TRANSIENT_SCID       = FIRST_POA_SCID + 4 ;
    public static final int SC_PERSISTENT_SCID      = makePersistent( SC_TRANSIENT_SCID ) ;
    public static final int IISC_TRANSIENT_SCID     = FIRST_POA_SCID + 8 ;
    public static final int IISC_PERSISTENT_SCID    = makePersistent( IISC_TRANSIENT_SCID ) ;
    public static final int MINSC_TRANSIENT_SCID    = FIRST_POA_SCID + 12 ;
    public static final int MINSC_PERSISTENT_SCID   = makePersistent( MINSC_TRANSIENT_SCID ) ;

    public static boolean isTransient( int scid )
    {
        return (scid & 2) == 0 ;
    }

    public static int makePersistent( int scid ) 
    {
        return scid | 2 ;
    }

    // Constants for ORB properties **************************************************************

    // All ORB properties must follow the following rules:
    // 1. Property names must start with either
    //    CORBA_PREFIX or SUN_PREFIX.
    // 2. Property names must have unique suffixes after the last ".".
    // 3. Property names must have "ORB" as the first 3 letters 
    //    in their suffix.
    // 4. proprietary property names should have a subsystem
    //    where appropriate after the prefix.

    // org.omg.CORBA properties must be defined by OMG standards
    // The well known org.omg.CORBA.ORBClass and
    // org.omg.CORBA.ORBSingletonClass are not included here
    // since they occur in org.omg.CORBA.ORB.

    public static final String CORBA_PREFIX = "org.omg.CORBA." ;

    public static final String INITIAL_HOST_PROPERTY =
        CORBA_PREFIX + "ORBInitialHost" ;
    public static final String INITIAL_PORT_PROPERTY =
        CORBA_PREFIX + "ORBInitialPort" ;
    public static final String INITIAL_SERVICES_PROPERTY =
        CORBA_PREFIX + "ORBInitialServices" ;
    public static final String DEFAULT_INIT_REF_PROPERTY =
        CORBA_PREFIX + "ORBDefaultInitRef" ;
    public static final String ORB_INIT_REF_PROPERTY =
        CORBA_PREFIX + "ORBInitRef" ;

    // All of our proprietary properties must start with com.sun.corba.ee
    public static final String SUN_PREFIX = "com.sun.corba.ee." ;

    // special property (system only) for enabling OSGIListener debug
    public static final String DEBUG_OSGI_LISTENER = SUN_PREFIX + "ORBDebugOSGIListener" ;

    // general properties
    public static final String ALLOW_LOCAL_OPTIMIZATION         = SUN_PREFIX + "ORBAllowLocalOptimization" ;
    public static final String SERVER_PORT_PROPERTY             = SUN_PREFIX + "ORBServerPort" ;
    public static final String SERVER_HOST_PROPERTY             = SUN_PREFIX + "ORBServerHost" ;
    public static final String ORB_ID_PROPERTY                  = CORBA_PREFIX + "ORBId" ;
    public static final String SKIP_GMBAL_INIT                  = CORBA_PREFIX + "SkipGmbalInit" ;
    // This property is provided for backward compatibility reasons
    public static final String OLD_ORB_ID_PROPERTY              = SUN_PREFIX + "ORBid" ;
    public static final String ORB_SERVER_ID_PROPERTY           = CORBA_PREFIX + "ORBServerId" ;
    public static final String DEBUG_PROPERTY                   = SUN_PREFIX + "ORBDebug" ;
    public static final String INIT_DEBUG_PROPERTY              = SUN_PREFIX + "ORBInitDebug" ;
    // Property for setting use of repository Ids during serialization.
    public static final String USE_REP_ID = SUN_PREFIX + "ORBUseRepId";

    // giop related properties - default settings in decimal form
    public static final String GIOP_VERSION                     = SUN_PREFIX + "giop.ORBGIOPVersion" ;
    public static final String GIOP_FRAGMENT_SIZE               = SUN_PREFIX + "giop.ORBFragmentSize" ;
    public static final String GIOP_BUFFER_SIZE                 = SUN_PREFIX + "giop.ORBBufferSize" ;
    public static final String GIOP_11_BUFFMGR                  = SUN_PREFIX + "giop.ORBGIOP11BuffMgr";
    public static final String GIOP_12_BUFFMGR                  = SUN_PREFIX + "giop.ORBGIOP12BuffMgr";
    public static final String GIOP_TARGET_ADDRESSING           = SUN_PREFIX + "giop.ORBTargetAddressing";    
    public static final int GIOP_DEFAULT_FRAGMENT_SIZE = 4096;
    public static final int GIOP_DEFAULT_BUFFER_SIZE = 4096;
    public static final int DEFAULT_GIOP_11_BUFFMGR = 0; //Growing
    public static final int DEFAULT_GIOP_12_BUFFMGR = 2; //Streaming
    public static final short ADDR_DISP_OBJKEY = 0; // object key used for target addressing
    public static final short ADDR_DISP_PROFILE = 1; // iop profile used for target addressing
    public static final short ADDR_DISP_IOR = 2; // ior used for target addressing
    public static final short ADDR_DISP_HANDLE_ALL = 3; // accept all target addressing dispositions (default)

    // CORBA formal 00-11-03 sections 15.4.2.2, 15.4.3.2, 15.4.6.2
    // state that the GIOP 1.2 RequestMessage, ReplyMessage, and
    // LocateReply message bodies must begin on 8 byte boundaries.
    public static final int GIOP_12_MSG_BODY_ALIGNMENT = 8;

    // The GIOP 1.2 fragments must be divisible by 8.  We generalize this
    // to GIOP 1.1 fragments, as well.
    public static final int GIOP_FRAGMENT_DIVISOR = 8;
    public static final int GIOP_FRAGMENT_MINIMUM_SIZE = 32;

    public static final String ENV_IS_SERVER_PROPERTY = 
        SUN_PREFIX + "ORBEnvironmentIsGlassFishServer" ;

    // connection management properties
    public static final String HIGH_WATER_MARK_PROPERTY =
        SUN_PREFIX + "connection.ORBHighWaterMark" ;
    public static final String LOW_WATER_MARK_PROPERTY =
        SUN_PREFIX + "connection.ORBLowWaterMark" ;
    public static final String NUMBER_TO_RECLAIM_PROPERTY =
        SUN_PREFIX + "connection.ORBNumberToReclaim" ;

    public static final String ACCEPTOR_CLASS_PREFIX_PROPERTY =
        SUN_PREFIX + "transport.ORBAcceptor";

    public static final String CONTACT_INFO_LIST_FACTORY_CLASS_PROPERTY =
        SUN_PREFIX + "transport.ORBContactInfoList";

    // Legacy:
    public static final String LEGACY_SOCKET_FACTORY_CLASS_PROPERTY =
        SUN_PREFIX + "legacy.connection.ORBSocketFactoryClass" ;


    public static final String SOCKET_FACTORY_CLASS_PROPERTY =
        SUN_PREFIX + "transport.ORBSocketFactoryClass" ;
    public static final String LISTEN_SOCKET_PROPERTY =
        SUN_PREFIX + "transport.ORBListenSocket";
    public static final String IOR_TO_SOCKET_INFO_CLASS_PROPERTY =
        SUN_PREFIX + "transport.ORBIORToSocketInfoClass";
    public static final String IIOP_PRIMARY_TO_CONTACT_INFO_CLASS_PROPERTY =
        SUN_PREFIX + "transport.ORBIIOPPrimaryToContactInfoClass";

    // Request partitioning maximum and minimum thread pool id constants.
    public static final int REQUEST_PARTITIONING_MIN_THREAD_POOL_ID =  0;
    public static final int REQUEST_PARTITIONING_MAX_THREAD_POOL_ID = 63;

    // Load balancing polices: integer to avoid issues with Enum encoding
    public static final int FIRST_LOAD_BALANCING_VALUE      = 0 ;
    public static final int NO_LOAD_BALANCING               = FIRST_LOAD_BALANCING_VALUE + 0 ;
    public static final int PER_REQUEST_LOAD_BALANCING      = FIRST_LOAD_BALANCING_VALUE + 1 ;
    public static final int LAST_LOAD_BALANCING_VALUE       = PER_REQUEST_LOAD_BALANCING ;

    // transport read tcp timeout property, colon separated property
    // with syntax <initial time to wait:max wait time
    // :backoff factor:max wait on a single sleep call>
    // where the last parameter is optional and defaults to INTEGER.MAX_VALUE.
    public static final String TRANSPORT_TCP_TIMEOUTS_PROPERTY =
        SUN_PREFIX + "transport.ORBTCPTimeouts";

    // Same syntax as TRANSPORT_TCP_TIMEOUTS_PROPERTY
    public static final String TRANSPORT_TCP_CONNECT_TIMEOUTS_PROPERTY = 
        SUN_PREFIX + "transport.ORBTCPConnectTimeouts" ;

    // initial time to wait in milliseconds in a temporary 
    // Selector.select(long wait) if a transport tcp read 
    // or write returns 0 bytes
    public static final int TRANSPORT_TCP_INITIAL_TIME_TO_WAIT = 2000;

    // max time to spend in cumulative waits in milliseconds
    // if a transport tcp read or write returns 0 bytes
    public static final int TRANSPORT_TCP_MAX_TIME_TO_WAIT = 6000;

    // A backoff percentage used to compute the next amount of time to
    // wait on a subsequent transport tcp read or write of 0 bytes.
    // It multiplies each wait time by 1.2.
    public static final int TRANSPORT_TCP_BACKOFF_FACTOR = 20;

    // initial time to wait in milliseconds after an attempt to connect
    // fails.
    public static final int TRANSPORT_TCP_CONNECT_INITIAL_TIME_TO_WAIT = 250 ;

    // max time to spend in cumulative waits in milliseconds
    // if a transport tcp read or write returns 0 bytes
    public static final int TRANSPORT_TCP_CONNECT_MAX_TIME_TO_WAIT = 60000;

    // A backoff percentage used to compute the next amount of time to
    // wait on a subsequent transport tcp read or write of 0 bytes.
    // It multiplies each wait time by 1.2.
    public static final int TRANSPORT_TCP_CONNECT_BACKOFF_FACTOR = 100;

    // Maximum time to wait before retrying to connect.
    public static final int TRANSPORT_TCP_CONNECT_MAX_SINGLE_WAIT = 5000 ;

    public static final String USE_NIO_SELECT_TO_WAIT_PROPERTY =
        SUN_PREFIX + "transport.ORBUseNIOSelectToWait";

    // "Socket" | "SocketChannel"
    // Note: Connections accepted by SocketChannel will be SocketChannel.
    public static final String ACCEPTOR_SOCKET_TYPE_PROPERTY =
        SUN_PREFIX + "transport.ORBAcceptorSocketType";

    // Applicable if using SocketChannel and using select thread.
    public static final String ACCEPTOR_SOCKET_USE_WORKER_THREAD_FOR_EVENT_PROPERTY =
        SUN_PREFIX + "transport.ORBAcceptorSocketUseWorkerThreadForEvent";

    // Applicable on client-side. "Socket" | "SocketChannel"
    public static final String CONNECTION_SOCKET_TYPE_PROPERTY = 
        SUN_PREFIX + "transport.ORBConnectionSocketType";

    // Applicable if using SocketChannel and using select thread
    public static final String CONNECTION_SOCKET_USE_WORKER_THREAD_FOR_EVENT_PROPERTY =
        SUN_PREFIX + "transport.ORBConnectionSocketUseWorkerThreadForEvent";

    // Used to disable the use of direct byte buffers.  This enables much easier
    // debugging, because the contents of a direct byte buffer cannot be 
    // viewed in most (all?) debuggers.
    public static final String DISABLE_DIRECT_BYTE_BUFFER_USE_PROPERTY =
        SUN_PREFIX + "transport.ORBDisableDirectByteBufferUse" ;

    // The default ByteBuffer size used to read data into when using the
    // optimized read strategy which is used when USE_NIO_SELECT_TO_WAIT_PROPERTY
    // is true, (default).
    public static final int DEFAULT_READ_BYTE_BUFFER_SIZE = 64000;
    
    // Property name to change the DEFAULT_READ_BYTE_BUFFER_SIZE value.
    public static final String READ_BYTE_BUFFER_SIZE_PROPERTY =
        SUN_PREFIX + "transport.ORBReadByteBufferSize";

    // The default pooled DirectByteBuffer slab size.
    public static final int DEFAULT_POOLED_DIRECT_BYTE_BUFFER_SLAB_SIZE = 
                                                         4000000; // 4 megabytes

    // The property name to set the size of the pooled DirectByteBuffer
    // slab size.
    public static final String POOLED_DIRECT_BYTE_BUFFER_SLAB_SIZE_PROPERTY =
            SUN_PREFIX + "transport.ORBPooledDirectByteBufferSlabSize";

    // Maximum read ByteBuffer size threshhold for which a single message will
    // be read.
    public static final int MAX_READ_BYTE_BUFFER_SIZE_THRESHOLD = 256000; // 256 kbytes

    // Property to override the default MAX_READ_BYTE_BUFFER_SIZE_THRESHOLD
    public static final String MAX_READ_BYTE_BUFFER_SIZE_THRESHOLD_PROPERTY =
            SUN_PREFIX + "transport.ORBMaximumReadByteBufferSize";

    // A property that can tell the optimized read implementation to always
    // enter a blocking read before returning control the Selector Thread.
    public static final String ALWAYS_ENTER_BLOCKING_READ_PROPERTY =
            SUN_PREFIX + "transport.ORBOptimizedReadAlwaysEnterBlockingRead";

    // A property that can tell the optimized read implementation to read
    // until zero bytes are read by the non-blocking read.
    public static final String NON_BLOCKING_READ_CHECK_MESSAGE_PARSER_PROPERTY =
            SUN_PREFIX + "transport.ORBNonBlockingReadCheckMessageParser";

    // A property that can tell the optimized read implementation to be more
    // patient about expecting additional data to arrive.
    public static final String BLOCKING_READ_CHECK_MESSAGE_PARSER_PROPERTY =
            SUN_PREFIX + "transport.ORBBlockingReadCheckMessageParser";

    public static final String SOCKET        = "Socket";
    public static final String SOCKETCHANNEL = "SocketChannel";

    public static final String WAIT_FOR_RESPONSE_TIMEOUT =
        SUN_PREFIX + "transport.ORBWaitForResponseTimeout";

    public static final String NO_DEFAULT_ACCEPTORS = 
        SUN_PREFIX + "transport.ORBNoDefaultAcceptors" ;

    public static final String REGISTER_MBEANS = 
        SUN_PREFIX + "ORBRegisterMBeans" ;

    public static final int DEFAULT_FRAGMENT_READ_TIMEOUT = 18000 ;

    public static final String FRAGMENT_READ_TIMEOUT = 
        SUN_PREFIX + "ORBFragmentReadTimeout" ;

    // POA related policies
    public static final String PERSISTENT_SERVER_PORT_PROPERTY  = 
        SUN_PREFIX + "POA.ORBPersistentServerPort" ;
    public static final String BAD_SERVER_ID_HANDLER_CLASS_PROPERTY = 
        SUN_PREFIX + "POA.ORBBadServerIdHandlerClass" ;
    public static final String ACTIVATED_PROPERTY               = 
        SUN_PREFIX + "POA.ORBActivated" ;
    public static final String SERVER_NAME_PROPERTY             = 
        SUN_PREFIX + "POA.ORBServerName" ;

    // Server Properties; e.g. when properties passed to ORB activated
    // servers

    public static final String SERVER_DEF_VERIFY_PROPERTY       = 
        SUN_PREFIX + "activation.ORBServerVerify" ;

    public static final String JTS_CLASS_PROPERTY               = 
        SUN_PREFIX + "CosTransactions.ORBJTSClass" ;

    // Property for enabling ORB's use of Java serialization.
    public static final String ENABLE_JAVA_SERIALIZATION_PROPERTY = 
        SUN_PREFIX + "encoding.ORBEnableJavaSerialization";

    // Constants for ORB prefixes **************************************************************

    public static final String PI_ORB_INITIALIZER_CLASS_PREFIX   =
        "org.omg.PortableInterceptor.ORBInitializerClass.";

    public static final String USER_CONFIGURATOR_PREFIX = 
        SUN_PREFIX + "ORBUserConfigurators." ;

    public static final String RFM_PROPERTY = USER_CONFIGURATOR_PREFIX 
        + "com.sun.corba.ee.impl.oa.rfm.ReferenceManagerConfigurator" ;

    public static final String USE_DYNAMIC_STUB_PROPERTY = 
        SUN_PREFIX + "ORBUseDynamicStub" ;

    public static final String DEBUG_DYNAMIC_STUB =
        SUN_PREFIX + "ORBDebugStubGeneration" ;
    
    // Not available through ORBData: used only in test.Test
    // and in the optional ClassCopierOrdinaryImpl class.
    public static final String USE_CODEGEN_REFLECTIVE_COPYOBJECT = 
        SUN_PREFIX + "ORBUseCodegenReflectiveCopyobject" ;

    public static final String DYNAMIC_STUB_FACTORY_FACTORY_CLASS =
        SUN_PREFIX + "ORBDynamicStubFactoryFactoryClass" ;
    
    // Constants for NameService properties ************************************

    public static final int DEFAULT_INITIAL_PORT                 = 900;

    public static final String DEFAULT_INS_HOST = "localhost";

    public static final int DEFAULT_INS_PORT                     = 2809;

    public static final int DEFAULT_INS_GIOP_MAJOR_VERSION       = 1;

    // http://www.omg.org/cgi-bin/doc?ptc/00-08-07 [ Section 13.6.7.3 ]
    // defines the default GIOP minor version to be 0.
    public static final int DEFAULT_INS_GIOP_MINOR_VERSION       = 0;


    // Constants for INS properties ********************************************

    // GIOP Version number for validation of INS URL format addresses
    public static final int MAJORNUMBER_SUPPORTED                 = 1;
    public static final int MINORNUMBERMAX                        = 2;

    // Subcontract's differentiation using the TRANSIENT and PERSISTENT
    // Name Service Property.
    public static final int TRANSIENT                             = 1;
    public static final int PERSISTENT                            = 2;

    // Constants for ORBD properties ****************************************************************

    // Used to disable ORBD, which is the normal case when the ORBFactory is used to create an ORB.
    public static final String DISABLE_ORBD_INIT_PROPERTY       = SUN_PREFIX + "activation.ORBDisableORBD" ;

    // These properties are never passed on ORB init: they are only passed to ORBD.

    public static final String DB_DIR_PROPERTY                  = SUN_PREFIX + "activation.DbDir" ;
    public static final String DB_PROPERTY                      = SUN_PREFIX + "activation.db" ;
    public static final String ORBD_PORT_PROPERTY               = SUN_PREFIX + "activation.Port" ;
    public static final String SERVER_POLLING_TIME              = SUN_PREFIX + "activation.ServerPollingTime";
    public static final String SERVER_STARTUP_DELAY             = SUN_PREFIX + "activation.ServerStartupDelay";

    public static final int DEFAULT_ACTIVATION_PORT             = 1049 ;

    // If RI is starting the NameService then they would indicate that by
    // passing the RI flag. That would start a Persistent Port to listen to
    // INS request.
    public static final int RI_NAMESERVICE_PORT                 = 1050;

    public static final int DEFAULT_SERVER_POLLING_TIME         = 1000;

    public static final int DEFAULT_SERVER_STARTUP_DELAY        = 1000;


    //***************** Constants for Logging ****************

    public static final String LOG_LEVEL_PROPERTY               = SUN_PREFIX + "ORBLogLevel";

    public static final String LOG_RESOURCE_FILE                = 
        "com.sun.corba.ee.impl.logging.LogStrings";

    public static final String SHOW_INFO_MESSAGES
        = SUN_PREFIX + "ORBShowInfoMessages";

    public static final String GET_SERVICE_CONTEXT_RETURNS_NULL
        = SUN_PREFIX + "ORBGetServiceContextReturnsNull";

    // Constants for initial references *************************************************************

    public static final String TRANSIENT_NAME_SERVICE_NAME = "TNameService" ;
    public static final String PERSISTENT_NAME_SERVICE_NAME = "NameService" ;

    // A large Number to make sure that other ServerIds doesn't collide
    // with NameServer Persistent Server Id
    public static final String NAME_SERVICE_SERVER_ID   = "1000000" ;

    public static final String ROOT_POA_NAME            = "RootPOA" ;
    public static final String POA_CURRENT_NAME         = "POACurrent" ;
    public static final String SERVER_ACTIVATOR_NAME    = "ServerActivator" ;
    public static final String SERVER_LOCATOR_NAME      = "ServerLocator" ;
    public static final String SERVER_REPOSITORY_NAME   = "ServerRepository" ;
    public static final String INITIAL_NAME_SERVICE_NAME= "InitialNameService" ;
    public static final String TRANSACTION_CURRENT_NAME = "TransactionCurrent" ;
    public static final String DYN_ANY_FACTORY_NAME     = "DynAnyFactory" ;
    public static final String REFERENCE_FACTORY_MANAGER= "ReferenceFactoryManager" ; 

    // New for Portable Interceptors
    public static final String PI_CURRENT_NAME          = "PICurrent" ;
    public static final String CODEC_FACTORY_NAME       = "CodecFactory" ;

    public static final String FOLB_CLIENT_GROUP_INFO_SERVICE = 
        "FolbClientGroupInfoService";

    public static final String FOLB_SERVER_GROUP_INFO_SERVICE = 
        "FolbServerGroupInfoService";

    public static final String CSI_V2_SSL_TAGGED_COMPONENT_HANDLER =
        "CSIv2SSLTaggedComponentHandler";


    // Constants for ORBD DB ***********************************************************************

    public static final String DEFAULT_DB_DIR       = "orb.db" ;
    public static final String DEFAULT_DB_NAME      = "db" ;
    public static final String INITIAL_ORB_DB       = "initial.db" ;
    public static final String SERVER_LOG_DIR       = "logs" ;
    public static final String ORBID_DIR_BASE       = "orbids" ;
    public static final String ORBID_DB_FILE_NAME   = "orbids.db" ;

    // Constants for minor code bases **************************************************************
    // This is the value that pre-Merlin Sun ORBs incorrectly used.  We preserve this
    // here for backwards compatibility, but note that the current ORB must never
    // create a BAD_PARAM system exception with this minor code.
    public static final int LEGACY_SUN_NOT_SERIALIZABLE = SUNVMCID.value + 1 ;

    // Code Set related *******************************************************

    // If we don't always send the code set context, there's a possibility
    // of failure when fragments of a smaller request are interleved with
    // those of a first request with other large service contexts.
    //
    public static final boolean DEFAULT_ALWAYS_SEND_CODESET_CTX = true;
    public static final String ALWAYS_SEND_CODESET_CTX_PROPERTY
        = SUN_PREFIX + "codeset.AlwaysSendCodeSetCtx";

    // Use byte order markers in streams when applicable?  This won't apply to
    // GIOP 1.1 due to limitations in the CDR encoding.
    public static final boolean DEFAULT_USE_BYTE_ORDER_MARKERS = true;
    public static final String USE_BOMS = SUN_PREFIX + "codeset.UseByteOrderMarkers";

    // Use byte order markers in encapsulations when applicable?
    public static final boolean DEFAULT_USE_BYTE_ORDER_MARKERS_IN_ENCAPS = false;
    public static final String USE_BOMS_IN_ENCAPS = SUN_PREFIX + "codeset.UseByteOrderMarkersInEncaps";

    // The CHAR_CODESETS and WCHAR_CODESETS allow the user to override the default
    // connection code sets.  The value should be a comma separated list of OSF
    // registry numbers.  The first number in the list will be the native code
    // set.
    //
    // Number can be specified as hex if preceded by 0x, otherwise they are
    // interpreted as decimal.
    //
    // Code sets that we accept currently (see core/OSFCodeSetRegistry):
    //
    // char/string:
    //
    // ISO8859-1 (Latin-1)     0x00010001
    // ISO646 (ASCII)          0x00010020
    // UTF-8                   0x05010001
    //
    // wchar/string:
    //
    // UTF-16                  0x00010109
    // UCS-2                   0x00010100
    // UTF-8                   0x05010001
    //
    // Note:  The ORB will let you assign any of the above values to
    // either of the following properties, but the above assignments
    // are the only ones that won't get you into trouble.
    public static final String CHAR_CODESETS = SUN_PREFIX + "codeset.charsets";
    public static final String WCHAR_CODESETS = SUN_PREFIX + "codeset.wcharsets";

    // Constants to make stream format version code easier to read
    public static final byte STREAM_FORMAT_VERSION_1 = (byte)1;
    public static final byte STREAM_FORMAT_VERSION_2 = (byte)2;

    // Constants used for denoting different serialization encodings.
    public static final byte CDR_ENC_VERSION = (byte) 0;
    public static final byte JAVA_ENC_VERSION = (byte) 1; 

    // property to check if the current ORB was created from within the app server
    public static final String APPSERVER_MODE = SUN_PREFIX + "ORBAppServerMode";

    // name for looking up the remote object for getting initial clister instance info
    public static final String INITIAL_GROUP_INFO_SERVICE = "INITIAL_GIS" ;

    // Enable the use of internal timing points.  This slows the ORB down slightly,
    // even if none of the individual timing points are themselves enabled.
    public static final String TIMING_POINTS_ENABLED = SUN_PREFIX 
        + "ORBEnableTimingPoints" ;

    // Enable the use of the EnumDesc class for marshaling enums.  Really only
    // present for testing.
    public static final String USE_ENUM_DESC = SUN_PREFIX 
        + "ORBUseEnumDesc" ;

    // Specify an AMXv3-compliant ObjectName (as a string) for the parent of the
    // ORB's gmbal root.
    public static final String GMBAL_ROOT_PARENT_NAME = SUN_PREFIX 
        + "ORBGmbalRootParentName" ;
}

// End of file.
