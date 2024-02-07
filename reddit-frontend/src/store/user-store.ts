import { create } from "zustand";
import { persist } from "zustand/middleware";
import { IUserProfile, IUserProfileResponse, IPostOverview, IPostOverviewResponse } from "./interface";
import { formatDate,  } from "./util";

interface IUserStore {
    user: IUserProfile;
    top3Posts: IPostOverview[];
    draftPosts: IPostOverview[];
    jwtToken1: string;
    jwtToken2: string;
    fetchUserProfile: () => Promise<IUserProfileResponse>;
    updateUserProfile: (user: IUserProfile) => void;
    getTop3Posts: () => Promise<IPostOverviewResponse[]>;
    getDraftPosts: () => Promise<IPostOverviewResponse[]>;
}
const domain = "http://localhost:8081"
const profileUrl = `${domain}/user-service/user/profile`;
const top3PostsUrl = `${domain}/post-reply-service/posts/top3`;
const draftUrl = `${domain}/post-reply-service//posts/unpublished/all`;

export const useUserStore = create<IUserStore>()(
    persist(
        (set, get) => ({
            user: {
                firstName: "Jeffrey",
                lastName: "You",
                email: "yqse521749@gmail.com",
                active: true as boolean,
                dateJoined: formatDate("2024-01-01T00:00:00.000Z"),
                type: "user",
                profileImageURL: "https://api.dicebear.com/7.x/miniavs/svg?seed=8",
                verified: true as boolean,
            },
            top3Posts: [],
            draftPosts: [],
            // admin user, id = 1, test only
            jwtToken1: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XX0.J2_B1Y8STCtF_8oQF0gndAklds6dezvR6SJocK-sB9g",
            // admin user, id = 2, test only
            jwtToken2: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.uiM5Llbx-FYb4rbwV33BR04lmpJpDP6Sq-uD73DWSxw",

            fetchUserProfile: async (): Promise<IUserProfileResponse> => {
                // const jwt = get().jwtToken;
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
                    set({ user: data.user });
                    return data;
                } catch (error) {
                    throw new Error('Failed to fetch user profile');
                }
            },
            updateUserProfile: (user: IUserProfile) => {
                set({ user: user });
            },
            getTop3Posts: async (): Promise<IPostOverviewResponse[]> => {
                try {
                    const response = await fetch(top3PostsUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch user profile');
                    }
                    // format date
                    const data: IPostOverviewResponse[] = await response.json();
                    const top3Posts: IPostOverview[] = data.map((post: IPostOverviewResponse) => {
                        post.dateCreated = formatDate(post.dateCreated);
                        return {
                            postId: post.postId,
                            title: post.title,
                            dateCreated: post.dateCreated,
                        } as IPostOverview;
                    });
                    console.log(top3Posts)
                    set({ top3Posts: top3Posts });
                    return data;
                } catch (error) {
                    throw new Error('Failed to fetch user profile');
                }
            },
            getDraftPosts: async (): Promise<IPostOverviewResponse[]> => {
                const businessLogic = async () => {
                    const response = await fetch(draftUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch user profile');
                    }
                    // format date
                    const data = await response.json();
                    const draftList: IPostOverview[] = data.map((post: IPostOverviewResponse) => {
                        post.dateCreated = formatDate(post.dateCreated);
                        return {
                            postId: post.postId,
                            title: post.title,
                            dateCreated: post.dateCreated,
                        } as IPostOverview;
                    });
                    console.log(draftList)
                    set({ draftPosts: draftList });
                    return data;
                }
                return await exceptionHandler(businessLogic)();
            
            },
        }),
        {
            name: "user_profile",
        }
    )
);



// Dependency Injection
const exceptionHandler = (fn: any) => {
    return async (...args : any[]) => {
        try {
            return await fn(...args)
        } catch (err) {
            console.error(err)
        }
    }
}






