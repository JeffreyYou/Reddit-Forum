// Store code
import { create } from 'zustand';
import { IPostDetail } from './interface.ts';

interface IPostStore {
    jwtToken: string;
    getPublishedPosts: () => Promise<IPostDetail[]>;
}

export const usePostStore = create<IPostStore>((set) => ({
    jwtToken: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.uiM5Llbx-FYb4rbwV33BR04lmpJpDP6Sq-uD73DWSxw",
    getPublishedPosts: async () => {
        try {
            const response = await fetch('http://localhost:8081/post-reply-service/posts/published/all', {
                headers: {
                    'Authorization': `Bearer ${usePostStore.getState().jwtToken}`,
                },
            });
            if (!response.ok) {
                throw new Error('Failed to fetch published posts');
            }
            const data: IPostDetail[] = await response.json();
            return data;
        } catch (error) {
            console.error('Failed to fetch published posts:', error);
            throw error;
        }
    },
}));
