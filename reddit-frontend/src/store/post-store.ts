import {create} from "zustand";
import {persist} from "zustand/middleware";
import {IPostDetail, IPostDetailResponse, IPutPostRequest, IUserProfile} from "./interface";
import {useUserStore} from "./user-store";

export interface IPost {
    postId: string;
    userId: number;
    dateCreated: string;
    title: string;
}
interface IPostStore {
    publishedPosts: IPostDetail[];
    publishedPostList: IPost[];
    deletedPosts: IPost[];
    bannedPosts: IPost[];
    jwtToken: string;
    setPostJwtToken:(token: string)=>void;
    fetchPublishedPosts: () => Promise<IPostDetailResponse[]>;
    fetchPublishedPostList: ()=> Promise<IPost[]>;
    fetchDeletedPosts: () => Promise<IPost[]>;
    fetchBannedPosts:()=>Promise<IPost[]>;
    banPost: (postid: string) => Promise<string>;
    recoverPost: (postid: string)=>Promise<string>;
    unbanPost:(postid: string)=>Promise<string>;
    putPublishedPost: (request: IPutPostRequest) => Promise<void>
    savePost: (request: IPutPostRequest) => Promise<void>

    allUsers: IUserProfile[];
    getAllUsers: ()=>Promise<IUserProfile[]>;
}

const domain = "http://localhost:8081/post-reply-service"; //edit
const publishUrl = `${domain}/posts/published/all`;
const getDeleteUrl = `${domain}/posts/deleted/all`;
const getBannedUrl =`${domain}/posts/banned/all`;

const userUrl = "http://localhost:8081/user-service/admin/user"; //edit

export const usePostStore = create<IPostStore>() (
    persist((set, get)=>({
        publishedPosts:  [],
        publishedPostList: [],
        deletedPosts: [],
        bannedPosts:[],
        allUsers:[],
        jwtToken: "",//useUserStore.getState().jwtToken,
        setPostJwtToken: (jwtToken: string)=>{
            set({jwtToken: jwtToken})
        },
        //jwtToken: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XX0.J2_B1Y8STCtF_8oQF0gndAklds6dezvR6SJocK-sB9g",
        fetchPublishedPosts: async (): Promise<IPostDetailResponse[]> => {
            const jwt = get().jwtToken;
            try {
                const response = await fetch(publishUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + jwt,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch published posts');
                }
                const data: IPostDetailResponse[] = await response.json();
                const allPublishedPosts: IPostDetail[] = data.map((data: IPostDetailResponse) => {
                    return {
                        postId: data.postId,
                        title: data.title,
                        dateCreated: data.dateCreated,
                        content: data.content,
                        dateModified: data.dateModified,
                        isArchived: data.isArchived,
                        status: data.status,
                    } as IPostDetail;
                });

                // const data: IPost[] = await response.json();
                // set({ publishedPosts: data.map(x=> {return { ...x, dateCreated: x.dateCreated.slice(0,10)}})});
                set({publishedPosts: allPublishedPosts})
                return data;
            } catch (error) {
                throw new Error('Failed to fetch published posts');
            }
        },
        fetchPublishedPostList: async (): Promise<IPost[]> => {
            const jwt = get().jwtToken;
            try {
                const response = await fetch(publishUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer `+ jwt,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch published posts');
                }
                const data: IPost[] = await response.json();
                set({ publishedPostList: data.map(x=> {return { ...x, dateCreated: x.dateCreated.slice(0,10)}})});
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
                        'Authorization': 'Bearer ' + jwt,
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
                        'Authorization': 'Bearer ' + jwt,
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
                        'Authorization': 'Bearer ' + jwt,
                    }
                });
                console.log(response);
                if (!response.ok) {
                    throw new Error('Failed to ban this post');
                }
                get().fetchBannedPosts();
                get().fetchPublishedPostList();
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
                        'Authorization': 'Bearer ' + jwt,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to unban this post');
                }
                get().fetchBannedPosts();
                get().fetchPublishedPostList();
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
                        'Authorization': 'Bearer ' + jwt,
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
        getAllUsers: async (): Promise<IUserProfile[]> => {
            const jwt = get().jwtToken;
            try {
                const response = await fetch(userUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + jwt,
                    }
                });
                if (!response.ok) {
                    throw new Error('Failed to get users info');
                }
                const data: {message:string, userList:IUserProfile[]} = await response.json();
                set({allUsers: data.userList});
                return data.userList;
            } catch (error) {
                throw new Error('Failed to fetch banned posts');
            }
        },
        putPublishedPost: async (request: IPutPostRequest) => {
            console.log(request)
            const jwt = get().jwtToken;
            try {
                const response = await fetch('http://localhost:8081/post-reply-service/posts/publish', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + jwt,
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
        savePost: async (request: IPutPostRequest) => {
            console.log(request)
            const jwt = get().jwtToken;
            try {
                const response = await fetch('http://localhost:8081/post-reply-service/posts/save', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + jwt,
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
        }
    }), {
            name:"post-store",
    })
)
