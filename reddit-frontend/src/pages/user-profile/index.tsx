import React, { useMemo } from 'react';
import { Button, Col, Row, Tabs, Modal, Alert, notification, DatePicker, Space, Input } from 'antd';
import { FilterOutlined } from '@ant-design/icons';
import type { NotificationArgsProps, DatePickerProps  } from 'antd';

import styles from './style.module.scss';
import { useEffect, useState } from 'react';
import UserProfileCard from '../../components/user-profile-card';
import { useUserStore } from '../../store/user-store';
import UserProfileEdit from '../../components/forms/user-edit-profile';
import UserEmailEdit from '../../components/forms/user-edit-email';

import { getDraftAndHistoryPostsItem, getTop3PostsItem } from './utils';

const Context = React.createContext({ name: 'Default' });
type NotificationType = 'success' | 'info' | 'warning' | 'error';
type NotificationPlacement = NotificationArgsProps['placement'];
const { Search } = Input;

const UserProfile = () => {

  //* User Store Settings
  const { user, top3Posts, draftPosts, historyPostsDisplay, fetchUserProfile, getTop3Posts, getDraftPosts, updateUserTemporaryProfile, getHistoryPosts, searchHistoryByKeyWord, searchHistoryByDate } = useUserStore();
  useEffect(() => {
    // getDraftPosts();
    // getTop3Posts();
    // fetchUserProfile();
    // getHistoryPosts();
  }, []);

  //* Notification Settings
  const [api, contextHolder] = notification.useNotification();
  const openNotification = (type: NotificationType, placement: NotificationPlacement, message: string, content: React.ReactNode) => {
    api[type]({
      message: message,
      description: <Context.Consumer>{({ name }) => content}</Context.Consumer>,
      placement
    });
  };

  const contextValue = useMemo(() => ({ name: 'Ant Design' }), []);

  //* Modal Settings
  const [isModalProfileOpen, setIsModalProfileOpen] = useState(false);
  const [isModalEmailOpen, setIsModalEmailOpen] = useState(false);
  const [isShowingAlert, setIsShowingAlert] = useState(true);
  const [buttonValue, setButtonValue] = useState('Yes, I understand');
  const showTable = () => {
    if (isShowingAlert) {
      setIsShowingAlert(false);
      setButtonValue('Update');
    } else {
      setIsModalEmailOpen(false);
      setIsShowingAlert(true);
      setButtonValue('Yes, I understand');
      openNotification('success', 'bottomLeft', 'Email Verification Sent!', "Please check your email to verify your new email address.");
      // openNotification('error', 'bottomLeft', 'Email Verification Failed!', "Please check your email to verify your new email address.");
    }
  }
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

  //* Filter Settings
  const [keyWork, setKeyWord] = useState('');
  const [date, setDate] = useState('');

  const filterByWord = () => {
    searchHistoryByKeyWord(keyWork);
    setKeyWord('')
  }
  const updateDate: DatePickerProps['onChange'] = (_, dateStr) => {
    setDate(dateStr);
  }
  const filterByDate = () => {
    searchHistoryByDate(date)
  }

  return (

    <>
      {/* User Profile */}
      <div className={styles.profile_wrapper} >
        <div className={styles.profile}>
          <Row style={{ height: '100%' }}>
            <Col span={14} style={{ height: '100%' }}>
              <div className={styles.left_wrapper}>
                <UserProfileCard user={user} editProfile={handleEditProfile} editEmail={handleEditEmail} />
                <Tabs className={styles.draft_card} defaultActiveKey="1" centered items={getDraftAndHistoryPostsItem(draftPosts, historyPostsDisplay)} />
                <div className={styles.filter}>
                    <Row align={'middle'} className={styles.filter_content}>
                      <Col span={12} >
                        <Space> <Search  placeholder="Search key word" value={keyWork} onChange={(e) => setKeyWord(e.target.value)} onSearch={filterByWord} /></Space>
                      </Col>
                      <Col span={12} >
                        <Space.Compact> <DatePicker onChange={updateDate} placeholder="Select date" /><Button icon={<FilterOutlined />} onClick={filterByDate}>Apply</Button></Space.Compact>
                      </Col>
                    </Row>
                </div>
              </div>
            </Col>
            <Col span={10} style={{ height: '100%' }}>
              <Tabs className={styles.posts_card} defaultActiveKey="1" centered items={getTop3PostsItem(top3Posts)} />
            </Col>
          </Row>
        </div>
      </div>
      {/* Notification & Modal */}
      <Context.Provider value={contextValue}>
        {contextHolder}
      </Context.Provider>
      <Modal centered title="Edit Profile" open={isModalProfileOpen} onCancel={handleCancelProfile}
        footer={[
          <Button key="back" onClick={handleCancelProfile}>Cancel</Button>,
          <Button key="update" onClick={handleEditProfile} type='primary'>Update</Button>,]}
      >
        <UserProfileEdit />
      </Modal>
      <Modal centered title="Email Change" open={isModalEmailOpen} onCancel={handleCancelEmail}
        width={700} style={{ fontSize: '20px' }}
        footer={[
          <Button key="back" onClick={handleCancelEmail}>Cancel</Button>,
          <Button key="update" onClick={showTable} type='primary'>{buttonValue}</Button>,
        ]}>
        {isShowingAlert ?
          <Alert
            message="Email Change"
            description="Are you sure you want to change your email address?"
            type="warning"
            showIcon
            style={{ margin: "40px 20px" }}
          /> : <UserEmailEdit />}
      </Modal>

    </>
  )
}

export default UserProfile