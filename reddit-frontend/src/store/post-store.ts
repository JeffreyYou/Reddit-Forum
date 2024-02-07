import {create} from "zustand";
import {persist} from "zustand/middleware";


export interface IPost {
    postId: string;
    userId: number;
    dateCreated: string;
    title: string;
}
interface IPostStore {
    publishedPosts: IPost[];
    deletedPosts: IPost[];
    bannedPosts: IPost[];
    jwtToken: string;

    fetchPublishedPosts: () => Promise<IPost[]>;
    fetchDeletedPosts: () => Promise<IPost[]>;
    fetchBannedPosts:()=>Promise<IPost[]>;
    banPost: (postid: string) => Promise<string>;
    recoverPost: (postid: string)=>Promise<string>;
    unbanPost:(postid: string)=>Promise<string>;
}

const domain = "http://localhost:8085/post-reply-service";
const publishUrl = `${domain}/posts/published/all`;
const getDeleteUrl = `${domain}/posts/deleted/all`;
const getBannedUrl =`${domain}/posts/banned/all`;


export const usePostStore = create<IPostStore>() (
    persist((set, get)=>({
        publishedPosts:  [],
        deletedPosts: [],
        bannedPosts:[],
        jwtToken: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XX0.J2_B1Y8STCtF_8oQF0gndAklds6dezvR6SJocK-sB9g",  //get token where
        fetchPublishedPosts: async (): Promise<IPost[]> => {
            const jwt = get().jwtToken;
            try {
                const response = await fetch(publishUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${jwt}`,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch published posts');
                }
                const data: IPost[] = await response.json();
                set({ publishedPosts: data.map(x=> {return { ...x, dateCreated: x.dateCreated.slice(0,10)}})});
                return data;
            } catch (error) {
                throw new Error('Failed to fetch published posts');
            }
        },
        fetchDeletedPosts: async (): Promise<IPost[]> => {
            const jwt = get().jwtToken;
            try {
                const response = await fetch(getDeleteUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${jwt}`,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch deleted posts');
                }
                const data: IPost[] = await response.json();
                set({ deletedPosts: data.map(x=> {return { ...x, dateCreated: x.dateCreated.slice(0,10)}})});
                return data;
            } catch (error) {
                throw new Error('Failed to fetch deleted posts');
            }
        },
        fetchBannedPosts: async (): Promise<IPost[]> => {
            const jwt = get().jwtToken;
            try {
                const response = await fetch(getBannedUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${jwt}`,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch banned posts');
                }
                const data: IPost[] = await response.json();
                set({ bannedPosts: data.map(x=> {return { ...x, dateCreated: x.dateCreated.slice(0,10)}})});
                return data;
            } catch (error) {
                throw new Error('Failed to fetch banned posts');
            }
        },
        banPost: async (postid): Promise<string> => {
            const jwt = get().jwtToken;
            const banUrl = `${domain}/posts/${postid}/ban`;
            try {
                const response = await fetch(banUrl, {
                    method: 'PATCH',
                    headers: {
                        'Authorization': `Bearer ${jwt}`,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to ban this post');
                }
                get().fetchBannedPosts();
                get().fetchPublishedPosts();
                    const data: string = await response.json();
                return data;
            } catch (error) {
                throw new Error('Failed to ban this post');
            }
        },
        unbanPost: async (postid): Promise<string> => {
            const jwt = get().jwtToken;
            const unbanUrl = `${domain}/posts/${postid}/unban`;
            try {
                const response = await fetch(unbanUrl, {
                    method: 'PATCH',
                    headers: {
                        'Authorization': `Bearer ${jwt}`,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to unban this post');
                }
                get().fetchBannedPosts();
                get().fetchPublishedPosts();
                const data: string = await response.json();
                return data;
            } catch (error) {
                throw new Error('Failed to unban this post');
            }
        },
        recoverPost: async (postid): Promise<string> => {
            const jwt = get().jwtToken;
            const recoverUrl = `${domain}/posts/${postid}/recover`;
            try {
                const response = await fetch(recoverUrl, {
                    method: 'PATCH',
                    headers: {
                        'Authorization': `Bearer ${jwt}`,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to recover this post');
                }
                get().fetchDeletedPosts();
                get().fetchPublishedPosts();
                const data: string = await response.json();
                return data;
            } catch (error) {
                throw new Error('Failed to recover this post');
            }
        },
    }), {
            name:"post-store",
    })
)
