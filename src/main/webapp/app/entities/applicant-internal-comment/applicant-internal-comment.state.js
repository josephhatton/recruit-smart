(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('applicant-internal-comment', {
        parent: 'entity',
        url: '/applicant-internal-comment',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'ApplicantInternalComments'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/applicant-internal-comment/applicant-internal-comments.html',
            controller: 'ApplicantInternalCommentController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('applicant-internal-comment-detail', {
        parent: 'applicant-internal-comment',
        url: '/applicant-internal-comment/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'ApplicantInternalComment'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/applicant-internal-comment/applicant-internal-comment-detail.html',
            controller: 'ApplicantInternalCommentDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'ApplicantInternalComment', function ($stateParams, ApplicantInternalComment) {
            return ApplicantInternalComment.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'applicant-internal-comment',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('applicant-internal-comment-detail.edit', {
        parent: 'applicant-internal-comment-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-internal-comment/applicant-internal-comment-dialog.html',
            controller: 'ApplicantInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['ApplicantInternalComment', function (ApplicantInternalComment) {
                return ApplicantInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant-internal-comment.new', {
        parent: 'applicant-internal-comment',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-internal-comment/applicant-internal-comment-dialog.html',
            controller: 'ApplicantInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('applicant-internal-comment', null, {reload: 'applicant-internal-comment'});
          }, function () {
            $state.go('applicant-internal-comment');
          });
        }]
      })
      .state('applicant-internal-comment.edit', {
        parent: 'applicant-internal-comment',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-internal-comment/applicant-internal-comment-dialog.html',
            controller: 'ApplicantInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['ApplicantInternalComment', function (ApplicantInternalComment) {
                return ApplicantInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('applicant-internal-comment', null, {reload: 'applicant-internal-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant-internal-comment.delete', {
        parent: 'applicant-internal-comment',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-internal-comment/applicant-internal-comment-delete-dialog.html',
            controller: 'ApplicantInternalCommentDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['ApplicantInternalComment', function (ApplicantInternalComment) {
                return ApplicantInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('applicant-internal-comment', null, {reload: 'applicant-internal-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
