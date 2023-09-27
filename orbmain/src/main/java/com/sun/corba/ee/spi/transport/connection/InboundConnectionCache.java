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

package com.sun.corba.ee.spi.transport.connection;

/**
 * A concurrent connection cache for passively created connections (e.g.
 * from an acceptor).  Here a Connection is an
 * abstraction of a Socket or SocketChannel: basically some sort of resource
 * that is expensive to acquire, and can be re-used freely.
 * The cache maintains a loose
 * upper bound on the number of cached connections, and reclaims connections as
 * needed.
 * <p>
 * This cache places minimal requirements on the Connections that it contains:
 * <ol>
 * <li>A Connection must implement a close() method.  This is called when idle
 * connections are reclaimed.
 * <li>A Connection must be usable as a HashMap key.
 * </ol>
 * <p>
 * Some simple methods are provided for monitoring the state of the cache:
 * numbers of busy and idle connections, and the total number of
 * connections in the cache.
 * <p>
 * Access is also provided to the cache configuration: maxParallelConnections,
 * highWaterMark, and numberToReclaim.  Currently these can only be set when
 * the cache is created.
 * <p>
 * XXX We may wish to make the cache configuration dynamically configurable.
 */
public interface InboundConnectionCache<C extends Connection> extends ConnectionCache<C> {
    /**
     * Mark a connection as busy because a request is being processed
     * on the connection.  The connection may or may not be previously
     * known to the cache when this method is called.
     * Busy connections cannot be reclaimed.
     * This provides an early indication that a Connection is in use,
     * before we know how many responses still need to be sent on
     * the Connection for this request.  This reduces the likelyhood
     * of reclaiming a connection on which we are processing a request.
     * <p>
     * Note that this problem is inherent in a distributed system.
     * We could in any case reclaim a connection AFTER a client
     * has sent a request but BEFORE the request is received.
     * Note that AFTER and BEFORE refer to global time which does
     * not really exist in a distributed system (or at least we
     * want to pretend it is not available).
     * <p>
     * XXX Should we age out connections?
     * This would require actual time stamps, rather than just an LRU queue.
     *
     * @param conn connection to mark as busy
     */
    void requestReceived(C conn);

    /**
     * Indicate that request processing has been completed for a request
     * received on conn.  This indicates that a Connection that received
     * a request as indicated in a previous call to requestReceived has
     * completed request processing for that request.  Responses may still
     * need to be sent.  Some number of
     * responses (usually 0 or 1) may be expected ON THE SAME CONNECTION
     * even for an idle connection.  We maintain a count of the number of
     * outstanding responses we expect for protocols that return the response
     * on the same connection on which the request was received.  This is
     * necessary to prevent reclamation of a Connection that is idle, but
     * still needed to send responses to old requests.
     *
     * @param conn connection to mark as completed
     * @param numResponseExpected responses expected
     */
    void requestProcessed(C conn, int numResponseExpected);

    /**
     * Inform the cache that a response has been sent on a particular
     * connection.
     * <p>
     * When a Connection is idle, and has no pending responses, it is
     * eligible for reclamation.
     *
     * @param conn connection that response has been sent on
     */
    void responseSent(C conn);
}
