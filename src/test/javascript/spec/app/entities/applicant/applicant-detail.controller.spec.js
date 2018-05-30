'use strict';

describe('Controller Tests', function() {

    describe('Applicant Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockApplicant, MockAddress, MockApplicantStatus, MockApplicantComment, MockSkill, MockWorkHistory, MockApplicantInternalComment, MockWorkStatus, MockActivity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockApplicant = jasmine.createSpy('MockApplicant');
            MockAddress = jasmine.createSpy('MockAddress');
            MockApplicantStatus = jasmine.createSpy('MockApplicantStatus');
            MockApplicantComment = jasmine.createSpy('MockApplicantComment');
            MockSkill = jasmine.createSpy('MockSkill');
            MockWorkHistory = jasmine.createSpy('MockWorkHistory');
            MockApplicantInternalComment = jasmine.createSpy('MockApplicantInternalComment');
            MockWorkStatus = jasmine.createSpy('MockWorkStatus');
            MockActivity = jasmine.createSpy('MockActivity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Applicant': MockApplicant,
                'Address': MockAddress,
                'ApplicantStatus': MockApplicantStatus,
                'ApplicantComment': MockApplicantComment,
                'Skill': MockSkill,
                'WorkHistory': MockWorkHistory,
                'ApplicantInternalComment': MockApplicantInternalComment,
                'WorkStatus': MockWorkStatus,
                'Activity': MockActivity
            };
            createController = function() {
                $injector.get('$controller')("ApplicantDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'recruitsmartApp:applicantUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
