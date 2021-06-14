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

	logIn, execCode, execCodeManual, downloadManualTest, submitManualTest, ContinuePlanTest, InsertPlanTest, SubmitTest, AddStudentToOnGoing, RemoveStudentFromOnGoing, CheckValidCode, CheckTest, lockTest, AddExecCodeToTestDB, GetTestQuestions, GetTestCode, CheckStudentID, GetExamDate, GotTestCode, TestQuestions, SubmittedTest, GotExamDate, CheckedCode, CheckedStudentID, CheckedTest, AddStudentToOnGoingOnline, RemoveStudentFromOnGoingOnline, lockTestTeacher, getManualTestDetails, addRequestForExtraTime, logOut, getExtraTime, DetailsExtraTime, addExtraTime, addExtraTimePrinciple, ;

}
