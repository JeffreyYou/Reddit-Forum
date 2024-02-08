import { IContactAdminRequest, IContactAdminResponse } from './interface';
import { create } from "zustand";

const baseUrl = "http://localhost:8081/message-service/contactus";

interface IContactAdminStore {
    jwtToken: string,
    submitContactAdminRequest: (request: IContactAdminRequest) => Promise<IContactAdminResponse>;
    setContactJwtToken:(token: string)=>void;
}

export const useContactAdminStore = create<IContactAdminStore>((set, get) => ({
    jwtToken: "",
    setContactJwtToken: (jwtToken: string)=>{
        set({jwtToken: jwtToken})
    },
    submitContactAdminRequest: async (request: IContactAdminRequest): Promise<IContactAdminResponse> => {
        try {
            const jwt = get().jwtToken;
            const response = await fetch(`${baseUrl}/submit`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + jwt,
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
