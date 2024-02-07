import { Card, Descriptions, Col, Row, Tabs, Divider } from 'antd';
import { SettingOutlined, EditOutlined, FundProjectionScreenOutlined, FormOutlined } from '@ant-design/icons';
import styles from './style.module.scss';

import { useEffect } from 'react';
import UserProfileCard from '../../components/user-profile-card';
import { useUserStore } from '../../store/user-store';
import PostOverviewCard from '../../components/post-overview-card';
import { IPostDetail } from '../../store/interface';

const UserProfile = () => {


  const { user, top3Posts, draftPosts, fetchUserProfile, getTop3Posts, getDraftPosts} = useUserStore();

  useEffect(() => {
    getDraftPosts();
    getTop3Posts();
    fetchUserProfile();
    // console.log(user)
  }, []);

  const getCard = (draftPosts: IPostDetail[]) => {
    return (
      <>
        {
          draftPosts.map((post: IPostDetail) => {
            return (
              <PostOverviewCard post={post} />
            )
          })
        }
      </>
    )
  }

  const postsItemDraft = [
    {
      label: <FormOutlined />,
      key: '1',
      children: getCard(draftPosts),
      icon: "Drafts"
    },
    {
      label: <FundProjectionScreenOutlined />,
      key: '2',
      children: getCard(top3Posts),
      icon: 'History'
    },
  ]
  const postsItemPost = [
    {
      label: <FormOutlined />,
      key: '1',
      children: getCard(draftPosts),
      icon: "Top 3 Posts"
    },
    {
      label: <FundProjectionScreenOutlined />,
      key: '2',
      children: getCard(top3Posts),
      icon: 'Deleted Posts'
    },
  ]


  return (
    <div className={styles.profile_wrapper} >
      <div className={styles.profile}>
        <Row style={{ height: '100%' }}>
          <Col span={14} style={{ height: '100%' }}>
            <Col span={24} style={{ height: '30%' }}>
              <UserProfileCard className={styles.user_card} user={user} />
            </Col>
            <Col span={24} style={{ height: 'calc(70% - 148px)' }}>
              <Tabs className={styles.draft_card} defaultActiveKey="1" centered items={postsItemDraft} />
            </Col>
          </Col>
          <Col span={10}>
              <Tabs className={styles.posts_card} defaultActiveKey="1" centered items={postsItemPost} />
          </Col>
        </Row>

      </div>
    </div>
  )
}

export default UserProfile