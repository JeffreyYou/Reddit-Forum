export interface IUserProfile {
    firstName: string;
    lastName: string;
    email: string;
    active: boolean;
    dateJoined: string;
    type: string;
    profileImageURL: string;
    verified: boolean;
}

export interface IUserProfileResponse {
    user: IUserProfile;
    message: string;
}

export interface IPostOverview {
    postId: number;
    title: string;
    dateCreated: string;
}

export interface IPostOverviewResponse {
    postId: number;
    title: string;
    dateCreated: string;
    responseStatus: {
        message: string;
        success: boolean;
    }
}

export interface IContactAdminRequest {
    subject: string;
    email: string;
    message: string;
}

export interface IContactAdminResponse {
    success: boolean;
    message: string;
}