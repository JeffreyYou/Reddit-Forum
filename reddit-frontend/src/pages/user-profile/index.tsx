import React, { useMemo } from 'react';
import { Button, Col, Row, Tabs, Modal, Space, Alert, Divider, notification, } from 'antd';
import type { NotificationArgsProps } from 'antd';

import { ExclamationCircleFilled, FundProjectionScreenOutlined, FormOutlined, FireOutlined, WarningOutlined } from '@ant-design/icons';
import styles from './style.module.scss';
import { createContext } from 'react';
import { useEffect, useState } from 'react';
import UserProfileCard from '../../components/user-profile-card';
import { useUserStore } from '../../store/user-store';
import PostOverviewCard from '../../components/post-overview-card';
import { IPostDetail } from '../../store/interface';
import UserProfileEdit from '../../components/forms/user-edit-profile';
import UserEmailEdit from '../../components/forms/user-edit-email';

type NotificationType = 'success' | 'info' | 'warning' | 'error';
type NotificationPlacement = NotificationArgsProps['placement'];

const Context = React.createContext({ name: 'Default' });


const UserProfile = () => {


  const { user, userTemporay, top3Posts, draftPosts, fetchUserProfile, getTop3Posts, getDraftPosts, updateUserTemporaryProfile } = useUserStore();
  const [isModalProfileOpen, setIsModalProfileOpen] = useState(false);
  const [isModalEmailOpen, setIsModalEmailOpen] = useState(false);
  const [isShowingAlert, setIsShowingAlert] = useState(true);
  const [buttonValue, setButtonValue] = useState('Yes, I understand');
  const [api, contextHolder] = notification.useNotification();

  const openNotification = (type: NotificationType, placement: NotificationPlacement) => {
    api[type]({
      message: `Email Verification Sent!`,
      description: <Context.Consumer>{({ name }) => "Please check our email address"}</Context.Consumer>,
      placement
    });
  };
  const contextValue = useMemo(() => ({ name: 'Ant Design' }), []);

  useEffect(() => {
    // getDraftPosts();
    // getTop3Posts();
    // fetchUserProfile();
    // console.log(user)
  }, []);

  const getCard = (draftPosts: IPostDetail[]) => {
    return (
      <>
        {
          draftPosts.map((post: IPostDetail) => {
            return (
              <PostOverviewCard post={post} key={post.postId} />
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
      label: <FireOutlined style={{ color: 'red' }} />,
      key: '1',
      children: getCard(top3Posts),
      icon: "Top 3 Posts"
    },
  ]
  const handleEditProfile = () => {
    updateUserTemporaryProfile(user);
    setIsModalProfileOpen(true);
  }
  const handleCancelProfile = () => {
    setIsModalProfileOpen(false);
  }
  const handleEditEmail = () => {
    updateUserTemporaryProfile(user);
    setIsModalEmailOpen(true);
    setIsShowingAlert(true);
  }
  const handleCancelEmail = () => {
    setIsModalEmailOpen(false);
  }

  const showTable = () => {
    if (isShowingAlert) {
      setIsShowingAlert(false);
      setButtonValue('Update');
    } else {
      setIsModalEmailOpen(false);
      setIsShowingAlert(true);
      setButtonValue('Yes, I understand');
      openNotification('success', 'bottomLeft');
      // openNotification('error', 'bottomLeft');
    }
  }




  return (

    <>
      <Context.Provider value={contextValue}>
        {contextHolder}
      </Context.Provider>

      <Modal centered title="Edit Profile" open={isModalProfileOpen} onCancel={handleCancelProfile}
        footer={[
          <Button key="back" onClick={handleCancelProfile}>Cancel</Button>,
          <Button key="update" onClick={handleEditProfile} type='primary'>Update</Button>,
        ]}
      >
        <UserProfileEdit />
      </Modal>
      {/* <Modal centered title=<Space size={'large'}> <WarningOutlined style={{fontSize: '30px',color:'#faad14'}} />Are you sure you want to change your email address?</Space> open={isModalEmailOpen} onCancel={handleCancelEmail} */}
      <Modal centered title="Email Change" open={isModalEmailOpen} onCancel={handleCancelEmail}
        width={700} style={{ fontSize: '20px' }}
        footer={[
          <Button key="back" onClick={handleCancelEmail}>Cancel</Button>,
          <Button key="update" onClick={showTable} type='primary'>{buttonValue}</Button>,
        ]}
      >
        {
          isShowingAlert ?
            <Alert
              message="Email Change"
              description="Are you sure you want to change your email address?"
              type="warning"
              showIcon
              style={{ margin: "40px 20px" }}
            /> : <UserEmailEdit />
        }

      </Modal>


      <div className={styles.profile_wrapper} >
        <div className={styles.profile}>
          <Row style={{ height: '100%' }}>
            <Col span={14} style={{ height: '100%' }}>
              <div className={styles.left_wrapper}>
                <UserProfileCard user={user} editProfile={handleEditProfile} editEmail={handleEditEmail} />
                <Tabs className={styles.draft_card} defaultActiveKey="1" centered items={postsItemDraft} />
              </div>

            </Col>
            <Col span={10} style={{ height: '100%' }}>
              <Tabs className={styles.posts_card} defaultActiveKey="1" centered items={postsItemPost} />
            </Col>
          </Row>

        </div>
      </div>

    </>
  )
}

export default UserProfile