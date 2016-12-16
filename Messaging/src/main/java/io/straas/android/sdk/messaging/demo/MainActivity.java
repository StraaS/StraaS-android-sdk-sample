package io.straas.android.sdk.messaging.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import io.straas.android.sdk.base.credential.CredentialFailReason;
import io.straas.android.sdk.messaging.ChatMode;
import io.straas.android.sdk.messaging.ChatroomManager;
import io.straas.android.sdk.messaging.Message;
import io.straas.android.sdk.messaging.MessagingError;
import io.straas.android.sdk.messaging.RawData;
import io.straas.android.sdk.messaging.User;
import io.straas.android.sdk.messaging.interfaces.EventListener;
import io.straas.android.sdk.messaging.ui.ChatroomInputView;
import io.straas.android.sdk.messaging.ui.ChatroomOutputView;
import io.straas.android.sdk.messaging.ui.interfaces.CredentialAuthorizeListener;
import io.straas.android.sdk.messaging.ui.interfaces.SignInListener;
import io.straas.sdk.demo.MemberIdentity;

public class MainActivity extends AppCompatActivity {

    private static final String CHATROOM_NAME = "test_chatroom";
    private ChatroomOutputView mChatroomOutputView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChatroomOutputView = (ChatroomOutputView) findViewById(R.id.chat_room);
        mChatroomOutputView.setCredentialAuthorizeListener(mCredentialAuthorizeListener);
        mChatroomOutputView.setEventListener(mEventListener);
        mChatroomOutputView.connect(CHATROOM_NAME, MemberIdentity.ME,
                0 /*ChatroomManager.IS_PERSONAL_CHAT|ChatroomManager.WITH_DATA_CHANNEL*/);
    }

    private CredentialAuthorizeListener mCredentialAuthorizeListener =
            new CredentialAuthorizeListener() {
                @Override
                public void onSuccess(ChatroomManager chatRoomManager) {
                    final ChatroomInputView chatroomInputView = (ChatroomInputView) findViewById(android.R.id.inputArea);
                    chatroomInputView.setChatroomManager(chatRoomManager);
                    chatroomInputView.setSendMessageListener(mSendMessageListener);
                    chatroomInputView.setSignInListener(mSignInListener);
                }

                @Override
                public void onFailure(Exception error) {

                }

                @Override
                public void onFailure(CredentialFailReason credentialFailReason) {

                }
            };

    private EventListener mEventListener = new EventListener() {
        @Override
        public void onConnectFailed(Exception error) {

        }


        @Override
        public void onError(Exception error) {

        }

        @Override
        public void onAggregatedDataAdded(SimpleArrayMap<String, Integer> map) {

        }

        @Override
        public void onConnected() {
            // if you enable ChatroomManager.WITH_DATA_CHANNEL flag
            //Ppap[] PPAP = new Ppap[2];
            //PPAP[0] = new Ppap();
            //PPAP[0].mIHaveA = new String[]{"pen", "apple"};
            //PPAP[0].Uh = "Apple Pen!";
            //
            //PPAP[1] = new Ppap();
            //PPAP[1].mIHaveA = new String[]{"pen", "pineapple"};
            //PPAP[1].Uh = "Pineapple Pen!";
            //
            //mChatroomOutputView.getChatroomManager().sendRawData(
            //        new RawData.Builder().setObject(PPAP).build());
        }

        @Override
        public void onRawDataAdded(RawData raw) {
            // if you enable ChatroomManager.WITH_DATA_CHANNEL flag
            //try {
            //    Ppap[] PPAP = raw.getJsonTextAsData(Ppap[].class);
            //    Log.d("raw data", String.format("I have a %s, I have an %s, Uh! %s",
            //            PPAP[0].mIHaveA[0], PPAP[0].mIHaveA[1], PPAP[0].Uh));
            //    Log.d("raw data", String.format("I have a %s, I have an %s, Uh! %s",
            //            PPAP[1].mIHaveA[0], PPAP[1].mIHaveA[1], PPAP[1].Uh));
            //} catch (IOException e) {
            //    e.printStackTrace();
            //} catch (JSONException e) {
            //    e.printStackTrace();
            //}
        }

        @Override
        public void onConnectFailed(MessagingError messagingError) {

        }

        @Override
        public void onError(MessagingError messagingError) {

        }

        @Override
        public void onDisconnected() {

        }

        @Override
        public void onChatWriteModeChanged(ChatMode chatMode) {

        }

        @Override
        public void onInputIntervalChanged(int interval) {

        }

        @Override
        public void onMessageAdded(Message message) {

        }

        @Override
        public void onMessageRemoved(String messageId) {

        }

        @Override
        public void onMessageFlushed() {

        }

        @Override
        public void onUserJoined(User[] users) {

        }

        @Override
        public void onUserUpdated(User[] users) {

        }

        @Override
        public void onUserLeft(Integer[] userLabels) {

        }

        @Override
        public void userCount(int userCount) {

        }
    };

    private OnCompleteListener<Void> mSendMessageListener = new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

        }
    };

    private SignInListener mSignInListener = new SignInListener() {
        @Override
        public void signIn() {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatroomOutputView.disconnect();
    }

    /**
     * Class for demo {@link RawData}
     */
    private static class Ppap {
        public String[] mIHaveA;
        public String Uh;
    }

}

