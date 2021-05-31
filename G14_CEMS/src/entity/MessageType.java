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
	insertTestBank,

	logIn, getTestBankName, getCourseID, getTestID, TestList, CheckTest, CheckedTest, CheckStudentID, CheckedStudentID, CheckValidCode, CheckedCode;

}
