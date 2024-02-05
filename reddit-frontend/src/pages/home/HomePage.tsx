import {Posts, samplePosts} from "../../components/Posts.tsx";
import {NewPost} from "../../components/NewPost.tsx";
import styles from './style.module.scss';

const HomePage = () => {
    // fixme: check if the current user is admin
    const isAdmin = false;

    const handleNewPost = () => {
        // Placeholder for future implementation
        alert('Open new post creation page or modal');
    };

    return (
        <div className={styles.homepage_container}>
            {isAdmin ? (
                <div>
                    <h1>Hello! This is your Admin homepage.</h1>
                    <Posts posts={samplePosts} />
                </div>
            ) : (
                <div className={styles.content}>
                    <h1>Hello! This is your User homepage.</h1>
                    <NewPost onAddPost={handleNewPost} />
                    <Posts posts={samplePosts} />
                </div>
            )}
        </div>
    );
};

export default HomePage;
