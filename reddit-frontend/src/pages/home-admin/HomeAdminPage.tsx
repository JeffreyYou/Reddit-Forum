import styles from './style.module.scss';
import {Button, Card} from "antd";
import { usePostStore} from "../../store/post-store";
import {useEffect} from "react";

const HomeAdminPage = () => {

    const {publishedPosts, fetchPublishedPosts} = usePostStore();
    const {deletedPosts, fetchDeletedPosts} = usePostStore();
    const {bannedPosts, fetchBannedPosts} = usePostStore();

    const {banPost, unbanPost, recoverPost} = usePostStore();

    useEffect(() => {
        fetchPublishedPosts();
        fetchBannedPosts();
        fetchDeletedPosts();
    },[]);

    const handleBan = (postid:string)=>{
        banPost(postid);
        //opennotification
    };

    const handleUnban = (postid: string)=>{
        unbanPost(postid);
    }
    const handleRecover = (postid: string)=>{
        recoverPost(postid);
    }

    return ( //
        <div className={styles.homepage_container}>
            <div>
                <h1>Hello! This is your Admin homepage.</h1>
            </div>

            <div className={styles.posts_container}>
                {publishedPosts.map((post) => (
                    <div key={post.postId}>
                    <Card
                        className="reddit-card"
                        hoverable
                    >
                        <h3 className="reddit-card-title">{post.title}</h3>
                        <p className="reddit-card-meta">{`${post.userId} - ${post.dateCreated}`}</p>

                    </Card>
                    <Button type="primary" onClick={()=>handleBan(post.postId)}> Ban this post </Button>
                    </div>
                ))}
            </div>

            <div className={styles.posts_container}>
                <h3> Deleted posts</h3>
                {deletedPosts.map((post) => (
                        <Card
                            key={post.postId}
                            className="reddit-card"
                            hoverable
                        >
                            <h3 className="reddit-card-title">{post.title}</h3>
                            <p className="reddit-card-meta">{`${post.userId} - ${post.dateCreated}`}</p>
                            <Button type="primary" onClick={()=>handleRecover(post.postId)}> Recover this post </Button>
                        </Card>
                ))}
            </div>

            <div className={styles.posts_container}>
                <h3> Banned posts</h3>
                {bannedPosts.map((post) => (
                    <Card
                        key={post.postId}
                        className="reddit-card"
                        hoverable
                    >
                        <h3 className="reddit-card-title">{post.title}</h3>
                        <p className="reddit-card-meta">{`${post.userId} - ${post.dateCreated}`}</p>
                        <Button type="primary" onClick={()=>handleUnban(post.postId)}> Unban this post </Button>
                    </Card>
                ))}
            </div>

        </div>
    );
};

export default HomeAdminPage;
