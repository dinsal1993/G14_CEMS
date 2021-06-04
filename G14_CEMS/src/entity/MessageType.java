package entity;

public enum MessageType {
	GetAllTests,
	UpdateTestDuration,
	TestsList,
	Error,
	SuccessUpdateTest,
	GetTestCount,
	TestCount,
	GetAllTestBanks,
	LockTest,
	SuccessLockTest,
	GetAllQuestionBank,
	RequestExtraTime,
	SentExtraTimeRequest,
	RefreshCourseTable,
	CourseList,
	AddCourse,
	CourseAdded,
	DeleteCourse,
	CourseDeleted,
	TestBanksList, QuestionBankList, addQuestion, insertQuestionBank,

	logIn, execCode, execCodeManual, downloadManualTest, submitManualTest;

}
