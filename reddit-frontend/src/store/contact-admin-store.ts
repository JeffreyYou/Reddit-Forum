import { IContactAdminRequest, IContactAdminResponse } from './interface';
import { create } from "zustand";

const baseUrl = "http://localhost:8081/message-service/contactus";

interface IContactAdminStore {
    jwtToken: string,
    submitContactAdminRequest: (request: IContactAdminRequest) => Promise<IContactAdminResponse>;
}

export const useContactAdminStore = create<IContactAdminStore>((_set, get) => ({
    // todo: update the jwt token
    jwtToken: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.uiM5Llbx-FYb4rbwV33BR04lmpJpDP6Sq-uD73DWSxw",
    submitContactAdminRequest: async (request: IContactAdminRequest): Promise<IContactAdminResponse> => {
        try {

            const response = await fetch(`${baseUrl}/submit`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization':  `Bearer ${get().jwtToken}`,
                },
                body: JSON.stringify(request),
            });
            if (!response.ok) {
                throw new Error('Failed to submit contact admin request');
            }
            const data: IContactAdminResponse = await response.json();
            return data;
        } catch (error) {
            console.error('Failed to submit contact admin request', error);
            throw error;
        }
    },
}));
