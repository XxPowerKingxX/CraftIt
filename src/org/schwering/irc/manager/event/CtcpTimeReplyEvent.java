/*
 * IRClib -- A Java Internet Relay Chat library -- class IRCConnection
 * Copyright (C) 2002 - 2006 Christoph Schwering <schwering@gmail.com>
 * 
 * This library and the accompanying materials are made available under the
 * terms of the
 * 	- GNU Lesser General Public License,
 * 	- Apache License, Version 2.0 and
 * 	- Eclipse Public License v1.0.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY.
 */

package org.schwering.irc.manager.event;

import org.schwering.irc.manager.Channel;
import org.schwering.irc.manager.Connection;
import org.schwering.irc.manager.User;

/**
 * Fired when a CTCP TIME reply has been received.
 *
 * @author Christoph Schwering &lt;schwering@gmail.com&gt;
 * @version 1.00
 * @see CtcpListener#timeReplyReceived(CtcpTimeReplyEvent)
 * @since 2.00
 */
public class CtcpTimeReplyEvent {
    private Connection connection;
    private User sender;
    private User destUser;
    private Channel destChannel;
    private String command;
    private String rest;

    public CtcpTimeReplyEvent(Connection connection, User sender, User destUser,
                              String command, String rest) {
        this.connection = connection;
        this.sender = sender;
        this.destUser = destUser;
        this.command = command;
        this.rest = rest;
    }

    public CtcpTimeReplyEvent(Connection connection, User sender,
                              Channel destChannel, String command, String rest) {
        this.connection = connection;
        this.sender = sender;
        this.destChannel = destChannel;
        this.command = command;
        this.rest = rest;
    }

    public Connection getConnection() {
        return connection;
    }

    public User getSender() {
        return sender;
    }

    public Channel getDestinationChannel() {
        return destChannel;
    }

    public User getDestinationUser() {
        return destUser;
    }

    public String getCommand() {
        return command;
    }

    public String getArguments() {
        return rest;
    }

    /**
     * Returns the time. The format is human-readable.
     */
    public String getTime() {
        return rest;
    }
}