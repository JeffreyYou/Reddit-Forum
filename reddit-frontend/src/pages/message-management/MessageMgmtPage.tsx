import React, { useEffect } from 'react';
import { Card, Switch } from 'antd';
import { IMessage } from '../../store/interface';
import { useMessageManagementStore } from '../../store/message-management-store';
import styles from './style.module.scss';

const MessageMgmtPage: React.FC = () => {
    const { messages, getAllMessages, openMessage, closeMessage } = useMessageManagementStore();

    useEffect(() => {
        getAllMessages();
    }, []);

    const handleStatusToggle = async (messageId: number, currentStatus: string) => {
        if (currentStatus === 'Open') {
            await closeMessage(messageId);
        } else {
            await openMessage(messageId);
        }
    };

    return (
        <div className={styles.message_mgmt_page}>
            <div className={styles.message_list}>
            <h2>Message Management Page</h2>
            {messages.map((message: IMessage) => (
                <Card key={message.messageId} className={styles.message_card}>
                    <p>Date created: {message.dateCreated}</p>
                    <p>Email address: {message.email}</p>
                    <p>Message: {message.message}</p>
                    <p>Status: {message.status}</p>
                    <p>Update Status: <Switch checked={message.status === 'Open'}
                                       onChange={() => handleStatusToggle(message.messageId, message.status)}/></p>
                </Card>
            ))}
            </div>
        </div>
    );
};

export default MessageMgmtPage;
