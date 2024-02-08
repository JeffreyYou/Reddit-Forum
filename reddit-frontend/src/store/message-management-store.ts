import { create } from 'zustand';

import { IMessage, IMessageListResponse } from './interface';

const baseUrl = 'http://localhost:8081/message-service/admin/messages';

interface IMessageManagementStore {
    jwtToken: string;
    messages: IMessage[];
    getAllMessages: () => Promise<void>;
    openMessage: (messageId: number) => Promise<void>;
    closeMessage: (messageId: number) => Promise<void>;
}

export const useMessageManagementStore = create<IMessageManagementStore>((set) => ({
    // todo : change admin jwt
    jwtToken: "",
    //jwtToken: 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XX0.J2_B1Y8STCtF_8oQF0gndAklds6dezvR6SJocK-sB9g',
    messages: [],
    getAllMessages: async () => {
        try {
            const response = await fetch(`${baseUrl}/list`, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem("jwtToken"),
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
        try {
            const response = await fetch(`${baseUrl}/open/${messageId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + localStorage.getItem("jwtToken"),
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
        try {
            const response = await fetch(`${baseUrl}/close/${messageId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + localStorage.getItem("jwtToken"),
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
