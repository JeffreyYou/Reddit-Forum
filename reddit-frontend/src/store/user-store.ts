import { create } from "zustand";
import { persist } from "zustand/middleware";

import { IUserProfile, IUserProfileResponse, IPostDetail, IPostDetailResponse } from "./interface";

import { formatDate, } from "./util";
import moment from 'moment';

interface IUserStore {
    //* User Profile
    jwtToken: string;
    user: IUserProfile;
    userTemporay: IUserProfile;
    setJwtToken: (token: string) => void;
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

    //* Update User Profile
    updateFirstName: (firstName: string) => Promise<void>;
    updateLastName: (lastName: string) => Promise<void>;
    updateEmail: (email: string) => Promise<void>;
    updateProfileImage: (profileImageURL: string) => Promise<void>;

}

export interface SetUserFieldRequest {
    fieldName: string;
    fieldValue: string;
}
const domain = "http://localhost:8081"
const profileUrl = `${domain}/user-service/user/profile`;
const top3PostsUrl = `${domain}/post-reply-service/posts/top3`;
const draftUrl = `${domain}/post-reply-service/posts/unpublished/all`;
const firstNameUrl = `${domain}/user-service/user/firstname`;
const lastNameUrl = `${domain}/user-service/user/lastname`;
const emailUrl = `${domain}/user-service/user/email`;
const profileImageUrl = `${domain}/user-service/user/profile-image-url`;

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
            jwtToken: "",
            top3Posts: [] as IPostDetail[],
            draftPosts: [] as IPostDetail[],
            historyPosts: [] as IPostDetail[],
            historyPostsDisplay: [] as IPostDetail[],
            historyPostsKeyword: [] as IPostDetail[],
            historyPostsDate: [] as IPostDetail[],

            setJwtToken: (token: string) => {
                set({ jwtToken: token });
            },

            fetchUserProfile: async (): Promise<IUserProfileResponse> => {
                try {
                    const response = await fetch(profileUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': 'Bearer ' + localStorage.getItem("jwtToken"),
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
                const businessLogic = async () => {
                    const response = await fetch(top3PostsUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': 'Bearer ' + localStorage.getItem("jwtToken"),
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
                            'Authorization': 'Bearer ' + localStorage.getItem("jwtToken"),
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
                            'Authorization': 'Bearer ' + localStorage.getItem("jwtToken"),
                            'Content-Type': 'application/json'
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
                    set({ historyPosts: historyList, historyPostsDisplay: historyList });
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
            updateFirstName: async (firstName: string) => {
                const requestData = {
                    fieldName: "firstName",
                    fieldValue: firstName
                } as SetUserFieldRequest;
                const response = await fetch(firstNameUrl, {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem("jwtToken"),
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(requestData),
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch user history posts');
                }
                console.log("firstName updated successfully!")

            },
            updateLastName: async (lastName: string) => {
                const requestData = {
                    fieldName: "lastName",
                    fieldValue: lastName
                } as SetUserFieldRequest;
                const response = await fetch(firstNameUrl, {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem("jwtToken"),
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(requestData),
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch user history posts');
                }
                console.log("lastName updated successfully!")
            },
            updateEmail: async (email: string) => {
                const user = get().userTemporay;
                user.email = email;
                set({ user: user });
            },
            updateProfileImage: async (profileImageURL: string) => {
                const requestData = {
                    fieldName: "profileImageURL",
                    fieldValue: profileImageURL
                } as SetUserFieldRequest;
                const response = await fetch(firstNameUrl, {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem("jwtToken"),
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(requestData),
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch user history posts');
                }
                console.log("profileImageURL updated successfully!")
            }
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






