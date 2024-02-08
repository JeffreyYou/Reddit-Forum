import { create } from "zustand";
import { persist } from "zustand/middleware";

import { IUserProfile, IUserProfileResponse, IPostDetail, IPostDetailResponse } from "./interface";

import { formatDate, } from "./util";
import moment from 'moment';

interface IUserStore {
    //* User Profile
    user: IUserProfile;
    userTemporay: IUserProfile;
    fetchUserProfile: () => Promise<IUserProfileResponse>;
    updateUserProfile: (user: IUserProfile) => void;
    updateUserTemporaryProfile: (user: IUserProfile) => void;
    
    //* All the Posts
    top3Posts: IPostDetail[];
    draftPosts: IPostDetail[];
    getTop3Posts: () => Promise<IPostDetailResponse[]>;
    getDraftPosts: () => Promise<IPostDetailResponse[]>;

    //* History Posts
    historyPosts: IPostDetail[];
    historyPostsDisplay: IPostDetail[];
    historyPostsKeyword: IPostDetail[];
    historyPostsDate: IPostDetail[];
    getHistoryPosts: () => Promise<IPostDetailResponse[]>;
    searchHistoryByKeyWord: (date: string) => void;
    searchHistoryByDate: (date: string) => void;

    //* Test Only
    jwtToken1: string;
    jwtToken2: string;



}
const domain = "http://localhost:8081"
const profileUrl = `${domain}/user-service/user/profile`;
const top3PostsUrl = `${domain}/post-reply-service/posts/top3`;
const draftUrl = `${domain}/post-reply-service/posts/unpublished/all`;

const userDefault: IUserProfile = {
    id: 1,
    firstName: "Jeffrey",
    lastName: "You",
    email: "yqse521749@gmail.com",
    active: true as boolean,
    dateJoined: formatDate("2024-01-01T00:00:00.000Z"),
    type: "user",
    profileImageURL: "https://api.dicebear.com/7.x/miniavs/svg?seed=8",
    verified: true as boolean,
}

export const useUserStore = create<IUserStore>()(
    persist(
        (set, get) => ({
            user: userDefault,
            userTemporay: userDefault,
            top3Posts: [] as IPostDetail[],
            draftPosts: [] as IPostDetail[],
            historyPosts: [] as IPostDetail[],
            historyPostsDisplay: [] as IPostDetail[],
            historyPostsKeyword: [] as IPostDetail[],
            historyPostsDate: [] as IPostDetail[],

            // admin user, id = 1, test only
            jwtToken1: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XX0.J2_B1Y8STCtF_8oQF0gndAklds6dezvR6SJocK-sB9g",
            // admin user, id = 2, test only
            jwtToken2: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.uiM5Llbx-FYb4rbwV33BR04lmpJpDP6Sq-uD73DWSxw",

            fetchUserProfile: async (): Promise<IUserProfileResponse> => {
                try {
                    const response = await fetch(profileUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch user profile');
                    }
                    // format date
                    const data: IUserProfileResponse = await response.json();
                    console.log(data)
                    data.user.dateJoined = formatDate(data.user.dateJoined);
                    // update user profile in the store
                    set({ user: data.user, userTemporay: data.user });
                    return data;
                } catch (error) {
                    throw new Error('Failed to fetch user profile');
                }
            },
            updateUserProfile: (user: IUserProfile) => {
                set({ user: user });
            },
            updateUserTemporaryProfile: (user: IUserProfile) => {
                set({ userTemporay: user });
            },
            getTop3Posts: async (): Promise<IPostDetailResponse[]> => {
                const businessLogic = async () =>{
                    const response = await fetch(top3PostsUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch user top 3 posts');
                    }
                    // format date

                    const data: IPostDetailResponse[] = await response.json();
                    const top3Posts: IPostDetail[] = data.map((data: IPostDetailResponse) => {
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
                    console.log(top3Posts)
                    // console.log(top3Posts)
                    set({ top3Posts: top3Posts });
                    return data;
                }

                return await exceptionHandler(businessLogic)();
            },
            getDraftPosts: async (): Promise<IPostDetailResponse[]> => {

                const businessLogic = async () => {
                    const response = await fetch(draftUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch user draft posts');
                    }
                    // format date

                    const data: IPostDetailResponse[] = await response.json();
                    const draftList: IPostDetail[] = data.map((data: IPostDetailResponse) => {
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
                    // console.log(draftList)
                    set({ draftPosts: draftList });
                    return data;
                }
                return await exceptionHandler(businessLogic)();

            },
            getHistoryPosts: async (): Promise<IPostDetailResponse[]> => {
                const historyUrl = `${domain}/composite-service/composite/all/1`;
                // const historyUrl = `${domain}/composite-service/composite/all/${get().user.id}`;
                const businessLogic = async () => {
                    const response = await fetch(historyUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch user history posts');
                    }
                    // format date

                    const data: IPostDetailResponse[] = await response.json();
                    const historyList: IPostDetail[] = data.map((data: IPostDetailResponse) => {
                        return {
                            postId: data.postId,
                            title: data.title,
                            dateCreated: data.dateCreated,
                            content: data.content,
                            dateModified: data.dateModified,
                            isArchived: data.isArchived,
                            status: data.status,
                            viewDate: data.viewDate,
                        } as IPostDetail;
                    });
                    set({ historyPosts: historyList, historyPostsDisplay: historyList});
                    // console.log(historyList)
                }
                return await exceptionHandler(businessLogic)();
            },
            searchHistoryByKeyWord: (keyword: string) => {
                const historyList: IPostDetail[] = get().historyPosts;
                const searchResult = historyList.filter((post: IPostDetail) => {
                    return post.title.includes(keyword) || post.content.includes(keyword);
                });
                set({ historyPostsDisplay: searchResult });
                
            },
            searchHistoryByDate: (date: string) => {
                if (date === "") {
                    set({ historyPostsDisplay: get().historyPosts });
                    return;
                }
                const historyList: IPostDetail[] = get().historyPosts;
                const searchResult = historyList.filter((post: IPostDetail) => {
                    const formatDate = moment(post.viewDate).format('YYYY-MM-DD');
                    return formatDate === date;
                    // console.log(post.dateCreated)
                });
                set({ historyPostsDisplay: searchResult });  
            },
        }),
        {
            name: "user_profile",
        }
    )
);



// Dependency Injection
const exceptionHandler = (fn: any) => {
    return async (...args: any[]) => {
        try {
            return await fn(...args)
        } catch (err) {
            console.error(err)
        }
    }
}






