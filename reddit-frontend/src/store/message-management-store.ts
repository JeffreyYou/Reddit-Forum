import { create } from 'zustand';

import { IMessage, IMessageListResponse } from './interface';

const baseUrl = 'http://localhost:8081/message-service/admin/messages';

interface IMessageManagementStore {
    jwtToken: string;
    messages: IMessage[];
    getAllMessages: () => Promise<void>;
    openMessage: (messageId: number) => Promise<void>;
    closeMessage: (messageId: number) => Promise<void>;
    setMessageJwtToken:(token: string)=>void;
}

export const useMessageManagementStore = create<IMessageManagementStore>((set, get) => ({
    jwtToken: "",
    setMessageJwtToken: (jwtToken: string)=>{
        set({jwtToken: jwtToken})
    },
    messages: [],
    getAllMessages: async () => {
        const jwt = get().jwtToken;
        try {
            const response = await fetch(`${baseUrl}/list`, {
                headers: {
                    'Authorization': 'Bearer ' + jwt,
                },
            });
            if (!response.ok) {
                throw new Error('Failed to fetch messages');
            }
            const data: IMessageListResponse = await response.json();
            set({ messages: data.messagesList });
        } catch (error) {
            console.error('Failed to fetch messages:', error);
            throw error;
        }
    },
    openMessage: async (messageId: number) => {
        const jwt = get().jwtToken;
        try {
            const response = await fetch(`${baseUrl}/open/${messageId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + jwt,
                },
            });
            if (!response.ok) {
                throw new Error('Failed to open message');
            }
            await useMessageManagementStore.getState().getAllMessages();
        } catch (error) {
            console.error('Failed to open message:', error);
            throw error;
        }
    },
    closeMessage: async (messageId: number) => {
        const jwt = get().jwtToken;
        try {
            const response = await fetch(`${baseUrl}/close/${messageId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + jwt,
                },
            });
            if (!response.ok) {
                throw new Error('Failed to close message');
            }
            await useMessageManagementStore.getState().getAllMessages();
        } catch (error) {
            console.error('Failed to close message:', error);
            throw error;
        }
    },
}));
