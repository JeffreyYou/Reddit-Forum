export interface IUserProfile {
    firstName: string;
    lastName: string;
    email: string;
    active: boolean;
    dateJoined: string;
    type: string;
    profileImageURL: string;
    verified: boolean;
    id: number;
}

export interface IUserProfileResponse {
    user: IUserProfile;
    message: string;
}

export interface IPostDetail {
    postId: string;
    title: string;
    dateCreated: Date;
    content: string;
    dateModified: Date;
    isArchived: boolean;
    status: string;
    
}

export interface IPostDetailResponse {
    postId: string;
    title: string;
    dateCreated: Date;
    content: string;
    dateModified: Date;
    isArchived: boolean;
    status: string;
    responseStatus: {
        message: string;
        success: boolean;
    }
}