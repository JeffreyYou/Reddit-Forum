import { create } from "zustand";
import { IPutPostRequest } from "./interface";

interface IFileStore {
    jwtToken: string
    imageUrls: string[]
    putImageUrl: (url: string) => Promise<void>
    putPublishedPost: (request: IPutPostRequest) => Promise<void>
}

export const useObjectStore = create<IFileStore>((set, get) => ({
    jwtToken: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.uiM5Llbx-FYb4rbwV33BR04lmpJpDP6Sq-uD73DWSxw",
    imageUrls: [],
    putImageUrl: async (url: string) => {
        const oldList: string[] = get().imageUrls
        set({imageUrls: [...oldList, url]});
    },
    putPublishedPost: async (request: IPutPostRequest) => {
        console.log(request)
        try {
            const response = await fetch('http://localhost:8081/post-reply-service/posts/publish', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${get().jwtToken}`,
                },
                body: JSON.stringify(request)
            });
            if (!response.ok) {
                throw new Error('Failed to fetch published posts');
            }
            const responseData: JSON = await response.json()
            console.log(responseData)
            //const data: IPostDetail[] = await response.json();
            //return data;
        } catch (error) {
            console.error('Failed to fetch published posts:', error);
            throw error;
        }
    },
}));
